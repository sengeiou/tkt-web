package com.mtl.cypw.web.service.member;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.member.client.CheckInUserApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.common.utils.MD5Util;
import com.mtl.cypw.domain.member.dto.CheckInUserDTO;
import com.mtl.cypw.domain.member.param.CheckInUserQueryParam;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.controller.member.converter.CheckInUserConverter;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.controller.member.param.CheckInUserLoginParam;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author tang.
 * @date 2020/2/12.
 */
@Slf4j
@Service
public class CheckInUserService {

    @Resource
    CheckInUserApiClient client;

    @Resource
    CheckInUserConverter converter;

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Resource
    private EnterpriseService enterpriseService;


    public TSingleResult<CheckInUserVO> checkInUserLogin(CheckInUserLoginParam param) {
        if (!param.checkParam()) {
            log.error("登录参数有误，param:{}", JSONObject.toJSONString(param));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        TSingleResult<CheckInUserVO> result = login(param);
        if (result.success()) {
            String accessToken = UUID.randomUUID().toString();
            result.getData().setAccessToken(accessToken);
            redisTemplate.saveBean(accessToken, result.getData(), Constants.tokenExpireTime());
            redisTemplate.saveBean(Constants.getUserTypeKey(accessToken), UserTypeEnum.CHECK_IN_USER, Constants.tokenExpireTime());
            redisTemplate.saveBean(Constants.getUserTokenKey(UserTypeEnum.CHECK_IN_USER.toString(), result.getData().getId()), accessToken, Constants.tokenExpireTime());
            return result;
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<CheckInUserVO> login(CheckInUserLoginParam param) {
        String userName = param.getUserName();
        String enterpriseAbbr = userName.substring(userName.indexOf("@") + 1, userName.length());
        if (userName.indexOf("@") < 1 || StringUtils.isEmpty(enterpriseAbbr)) {
            log.error("用户名错误，userName:{}", JSONObject.toJSONString(userName));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_USER_NAME.getCode(), ErrorCode.ERROR_COMMON_USER_NAME.getMsg());
        }
        EnterpriseDTO enterprise = enterpriseService.getEnterpriseInfo(enterpriseAbbr);
        if (enterprise == null) {
            log.error("用户名错误，enterpriseAbbr:{}", JSONObject.toJSONString(enterpriseAbbr));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_USER_NAME.getCode(), ErrorCode.ERROR_COMMON_USER_NAME.getMsg());
        }
        userName = userName.substring(0, userName.indexOf("@"));
        String userPass  = MD5Util.md5(param.getUserPass());
        CheckInUserQueryParam queryParam = new CheckInUserQueryParam();
        queryParam.setEnterpriseId(enterprise.getEnterpriseId());
        queryParam.setUserName(userName);
        queryParam.setUserPass(userPass);
        QueryRequest<CheckInUserQueryParam> request = QueryRequest.build();
        request.setParam(queryParam);
        TSingleResult<CheckInUserDTO> result = client.checkInUserLogin(request);
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }
}
