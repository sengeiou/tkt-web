package com.mtl.cypw.web.interceptor;

import com.juqitech.service.utils.DateUtils;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.exception.PermissionException;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.admin.vo.UserVO;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.controller.member.vo.MachineVO;
import com.mtl.cypw.web.controller.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tang.
 * @date 2019/11/28.
 */
@Slf4j
@CrossOrigin
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.debug("开始拦截");
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(accessToken)) {
            UserTypeEnum userType = redisTemplate.getBean(Constants.getUserTypeKey(accessToken), UserTypeEnum.class);
            if (UserTypeEnum.MEMBER.equals(userType)) {
                handleMemberRequest(accessToken);
            } else if (UserTypeEnum.CHECK_IN_USER.equals(userType)) {
                handleCheckInUserRequest(accessToken);
            } else if (UserTypeEnum.MACHINE.equals(userType)) {
                handleMachineRequest(accessToken);
            } else if (UserTypeEnum.ADMIN_USER.equals(userType)) {
                handleAdminUserRequest(accessToken);
            } else {
                throw new PermissionException(ErrorCode.ERROR_COMMON_TOKEN.getMsg(), ErrorCode.ERROR_COMMON_TOKEN.getCode());
            }
        } else {
            String enterpriseId = request.getHeader("enterpriseId");
            if (StringUtils.isNotEmpty(enterpriseId)) {
                Operator.setEnterpriseId(Integer.parseInt(enterpriseId));
            } else {
                throw new PermissionException(ErrorCode.ERROR_COMMON_NOT_ENTERPRISE.getMsg(), ErrorCode.ERROR_COMMON_NOT_ENTERPRISE.getCode());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Operator.remove();
    }

    private void handleMemberRequest(String accessToken) throws PermissionException {
        MemberVO member = redisTemplate.getBean(accessToken, MemberVO.class);
        if (member != null) {
            Operator.setMemberId(member.getMemberId());
            Operator.setUserId(member.getMemberId());
            Operator.setEnterpriseId(member.getEnterpriseId());
            redisTemplate.saveBean(accessToken, member, Constants.tokenExpireTime());
        } else {
            throw new PermissionException(ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getMsg(), ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getCode());
        }
    }

    private void handleCheckInUserRequest(String accessToken) throws PermissionException {
        CheckInUserVO vo = redisTemplate.getBean(accessToken, CheckInUserVO.class);
        if (vo != null) {
            if (vo.getBeginDate().compareTo(DateUtils.now()) == 1 || vo.getEndDate().compareTo(DateUtils.now()) == -1) {
                throw new PermissionException(ErrorCode.ERROR_COMMON_USER_NOT_EFFECTIVE_TIME.getMsg(), ErrorCode.ERROR_COMMON_USER_NOT_EFFECTIVE_TIME.getCode());
            }
            Operator.setUserId(vo.getId());
            Operator.setEnterpriseId(vo.getEnterpriseId());
            redisTemplate.saveBean(accessToken, vo, Constants.tokenExpireTime());
        } else {
            throw new PermissionException(ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getMsg(), ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getCode());
        }
    }

    private void handleMachineRequest(String accessToken) throws PermissionException {
        MachineVO vo = redisTemplate.getBean(accessToken, MachineVO.class);
        if (vo != null) {
            Operator.setUserId(vo.getMachineId());
            Operator.setEnterpriseId(vo.getEnterpriseId());
            redisTemplate.saveBean(accessToken, vo, Constants.tokenExpireTime());
        } else {
            throw new PermissionException(ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getMsg(), ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getCode());
        }
    }

    private void handleAdminUserRequest(String accessToken) throws PermissionException {
        UserVO vo = redisTemplate.getBean(accessToken, UserVO.class);
        if (vo != null) {
            Operator.setUserId(vo.getUserId());
            Operator.setEnterpriseId(vo.getEnterpriseId());
            redisTemplate.saveBean(accessToken, vo, Constants.tokenExpireTime());
        } else {
            throw new PermissionException(ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getMsg(), ErrorCode.ERROR_COMMON_SESSION_EXPIRE.getCode());
        }
    }
}
