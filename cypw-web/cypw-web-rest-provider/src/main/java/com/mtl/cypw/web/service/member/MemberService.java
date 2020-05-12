package com.mtl.cypw.web.service.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.member.client.MemberApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.exception.BusinessException;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.common.utils.AesUtil;
import com.mtl.cypw.common.utils.Base64Util;
import com.mtl.cypw.common.utils.config.AesProperties;
import com.mtl.cypw.domain.auth.dto.CmbAuthDTO;
import com.mtl.cypw.domain.member.dto.MemberDTO;
import com.mtl.cypw.domain.member.param.MemberLoginParam;
import com.mtl.cypw.domain.member.param.MemberParam;
import com.mtl.cypw.domain.member.param.MemberQueryParam;
import com.mtl.cypw.domain.mpm.constant.CacheConstant;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.domain.mpm.enums.LoginTypeEnum;
import com.mtl.cypw.domain.wx.response.MiniappSessionResult;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.member.converter.MemberConverter;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.controller.member.param.LoginParam;
import com.mtl.cypw.web.controller.member.vo.MemberVO;
import com.mtl.cypw.web.service.auth.CmbService;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import com.mtl.cypw.web.service.wx.MiniappService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Service
@Slf4j
public class MemberService {

    @Autowired
    MemberApiClient memberApiClient;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Resource
    private EnterpriseService enterpriseService;

    @Resource
    private CmbService cmbService;

    @Resource
    private MiniappService miniappService;

    @Value("${aes.key}")
    private String aesKey;

    @Value("${aes.iv}")
    private String aesIv;

    public TSingleResult<MemberVO> getMember() {
        MemberQueryParam query = new MemberQueryParam();
        query.setMemberId(Operator.getMemberId());
        QueryRequest<MemberQueryParam> request = QueryRequest.build();
        request.setParam(query);
        TSingleResult<MemberDTO> dto = memberApiClient.getMemberById(request);
        if (dto.success()) {
            return memberConverter.toVo(dto);
        } else {
            return ResultBuilder.failTSingle(dto.getStatusCode(), dto.getComments());
        }
    }

    public MemberDTO getMember2() {
        MemberQueryParam query = new MemberQueryParam();
        query.setMemberId(Operator.getMemberId());
        QueryRequest<MemberQueryParam> request = QueryRequest.build();
        request.setParam(query);
        TSingleResult<MemberDTO> dto = memberApiClient.getMemberById(request);
        if (dto.success()) {
            return dto.getData();
        }
        return null;
    }

    public TSingleResult<MemberVO> registerAndLogin(LoginParam param) {
        if (!param.checkParam()) {
            log.error("登录参数有误，param:{}", JSONObject.toJSONString(param));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        TSingleResult<MemberVO> result = registerAndLogin2(param);
        if (result.success()) {
            if (result.getData() != null) {
                String accessToken = UUID.randomUUID().toString();
                result.getData().setAccessToken(accessToken);
                redisTemplate.saveBean(accessToken, result.getData(), Constants.tokenExpireTime());
                redisTemplate.saveBean(Constants.getUserTypeKey(accessToken), UserTypeEnum.MEMBER, Constants.tokenExpireTime());
                return result;
            } else {
                return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getCode(), ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getMsg());
            }
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<MemberVO> registerAndLogin2(LoginParam param) {
        if (LoginTypeEnum.JINGYI.equals(param.getLoginType())) {
            return jingYiLogin(param);
        } else if (LoginTypeEnum.CMBAUTH.equals(param.getLoginType())) {
            return cmbAuth(param);
        } else if (LoginTypeEnum.MINIAPP.equals(param.getLoginType())) {
            return miniappLogin(param);
        } else {
            return normalLogin(param);
        }
    }

    private TSingleResult<MemberVO> normalLogin(LoginParam loginParam) {
        EnterpriseDTO dto = enterpriseService.getCacheEnterpriseInfo(Operator.getEnterpriseId());
        if (dto == null) {
            log.error("没有找到商户，enterpriseId:{}", Operator.getEnterpriseId());
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_AUTHORITY.getCode(), ErrorCode.ERROR_COMMON_AUTHORITY.getMsg());
        }
        String verifyCode = redisTemplate.getString(CacheConstant.getVerifyCodeKey(loginParam.getMemberMobile()));
        if (!loginParam.getVerifyCode().equals(verifyCode)) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_VERIFY_CODE.getCode(), ErrorCode.ERROR_COMMON_VERIFY_CODE.getMsg());
        }
        if (dto.getSupportNormalLogin() != 1) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_NOT_SUPPORT_LOGIN_MODE.getCode(), "无法登录，仅供内部使用");
        }
        MemberLoginParam param = new MemberLoginParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setLoginType(loginParam.getLoginType());
        param.setMemberMobile(loginParam.getMemberMobile());
        return login(param);
    }

    private TSingleResult<MemberVO> jingYiLogin(LoginParam loginParam) {
        AesProperties properties = new AesProperties();
        properties.setKey(aesKey);
        properties.setIv(aesIv);
        String info = AesUtil.decrypt(properties, loginParam.getEncodeParam());
        if (StringUtils.isEmpty(info)) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), "无法登录，仅供内部使用");
        }
        JSONObject jsonObject = JSON.parseObject(info);
        String userCode = jsonObject.getString("usercode");
        if (StringUtils.isEmpty(userCode)) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), "无法登录，仅供内部使用");
        }
        MemberLoginParam param = new MemberLoginParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setLoginType(loginParam.getLoginType());
        param.setMemberMobile(loginParam.getMemberMobile());
        param.setThirdPartyUserId(userCode);
        return login(param);
    }

    private TSingleResult<MemberVO> cmbAuth(LoginParam loginParam) {
        String encodeParam = loginParam.getEncodeParam();
        log.info("cmb auth encodeParam:{}", encodeParam);
        TSingleResult<CmbAuthDTO> result = cmbService.checkSign(encodeParam);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        CmbAuthDTO dto = result.getData();
        if (dto == null || dto.getData() == null || dto.getData().getCustomerInfo() == null || StringUtils.isEmpty(dto.getData().getCustomerInfo().getMobile2())) {
            log.error("为获取到用户信息，招行授权登录失败，dto:{}", JSONObject.toJSONString(dto));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_AUTO_LOGIN.getCode(), ErrorCode.ERROR_AUTO_LOGIN.getMsg());
        }
        MemberLoginParam param = new MemberLoginParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setLoginType(loginParam.getLoginType());
        param.setMemberMobile(dto.getData().getCustomerInfo().getMobile2());
        return login(param);
    }

    private TSingleResult<MemberVO> miniappLogin(LoginParam loginParam) {
        MemberLoginParam param = new MemberLoginParam();
        if (StringUtils.isNotEmpty(loginParam.getMemberMobile())) {
            param.setMemberMobile(loginParam.getMemberMobile());
        } else {
            try {
                String[] phoneAndOpenId = getPhoneAndOpenId(loginParam);
                param.setMemberMobile(phoneAndOpenId[0]);
                param.setOpenId(phoneAndOpenId[1]);
            } catch (BusinessException e) {
                return ResultBuilder.failTSingle(e.getCode(), e.getMessage());
            }
        }
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setLoginType(loginParam.getLoginType());
        return login(param);
    }

    private String[] getPhoneAndOpenId(LoginParam loginParam) throws BusinessException {
        TSingleResult<MiniappSessionResult> sessionResult = miniappService.getJscode2Session(loginParam.getCode());
        if (sessionResult.success()) {
            MiniappSessionResult result = sessionResult.getData();
            if (result != null) {
                AesProperties properties = new AesProperties();
                properties.setBKey(Base64Util.decode(result.getSessionKey()));
                properties.setBiv(Base64Util.decode(loginParam.getIv()));
                String info = AesUtil.decrypt(properties, Base64Util.decode(loginParam.getEncodeParam()));
                JSONObject userInfo = (JSONObject) JSONObject.parse(info);
                String userPhone = userInfo.getString("phoneNumber");
                return new String[]{userPhone, result.getOpenid()};
            } else {
                throw new BusinessException(ErrorCode.ERROR_COMMON_PARAMETER.getMsg(), ErrorCode.ERROR_COMMON_PARAMETER.getCode());
            }
        } else {
            throw new BusinessException(sessionResult.getComments(), sessionResult.getStatusCode());
        }
    }

    private TSingleResult<MemberVO> login(MemberLoginParam loginParam) {
        QueryRequest<MemberLoginParam> request = QueryRequest.build();
        request.setParam(loginParam);
        log.debug("会员注册且登录，request：{}", JSONObject.toJSONString(request));
        TSingleResult<MemberDTO> result = memberApiClient.registerAndLogin(request);
        if (result.success()) {
            return memberConverter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    public TSingleResult<Boolean> updateMember(GenericRequest<MemberParam> request) {
        log.debug("修改会员信息，request：{}", JSONObject.toJSONString(request));
        return memberApiClient.updateMember(request);
    }

}
