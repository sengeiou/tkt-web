package com.mtl.cypw.web.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.admin.client.UserApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.common.utils.MD5Util;
import com.mtl.cypw.domain.admin.dto.UserDTO;
import com.mtl.cypw.domain.admin.param.UserLoginParam;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.controller.admin.converter.UserConverter;
import com.mtl.cypw.web.controller.admin.param.AdminUserLoginParam;
import com.mtl.cypw.web.controller.admin.vo.UserVO;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author tang.
 * @date 2020/3/18.
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserApiClient userApiClient;

    @Resource
    private UserConverter userConverter;

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Resource
    private EnterpriseService enterpriseService;


    public TSingleResult<UserVO> adminUserLogin(AdminUserLoginParam param) {
        if (!param.checkParam()) {
            log.error("登录参数有误，param:{}", JSONObject.toJSONString(param));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        TSingleResult<UserVO> result = login(param);
        if (result.success()) {
            String accessToken = redisTemplate.getString(Constants.getUserTokenKey(UserTypeEnum.ADMIN_USER.toString(), result.getData().getUserId()));
            if (StringUtils.isNotEmpty(accessToken)) {
                if (param.getIsForce() == 1) {
                    redisTemplate.delete(Constants.getUserTokenKey(UserTypeEnum.ADMIN_USER.toString(), result.getData().getUserId()));
                } else {
                    return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_REPEAT_LOGIN.getCode(), ErrorCode.ERROR_COMMON_REPEAT_LOGIN.getMsg());
                }
            }
            accessToken = UUID.randomUUID().toString();
            result.getData().setAccessToken(accessToken);
            redisTemplate.saveBean(accessToken, result.getData(), Constants.tokenExpireTime());
            redisTemplate.saveBean(Constants.getUserTypeKey(accessToken), UserTypeEnum.ADMIN_USER, Constants.tokenExpireTime());
            redisTemplate.saveBean(Constants.getUserTokenKey(UserTypeEnum.ADMIN_USER.toString(), result.getData().getUserId()), accessToken, Constants.tokenExpireTime());
            return result;
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<UserVO> login(AdminUserLoginParam param) {
        String userName = param.getLoginName();
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
        String userPass = MD5Util.md5(param.getLoginPass());
        UserLoginParam loginParam = new UserLoginParam();
        loginParam.setEnterpriseId(enterprise.getEnterpriseId());
        loginParam.setLoginName(userName);
        loginParam.setLoginPass(userPass);
        GenericRequest<UserLoginParam> request = new GenericRequest();
        request.setParam(loginParam);
//        TSingleResult<UserDTO> result = userApiClient.login(request);
//        if (result.success()) {
//            return userConverter.toVo(result);
//        } else {
//            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
//        }
        return null;
    }
}
