package com.mtl.cypw.web.interceptor;

import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.exception.PermissionException;
import com.mtl.cypw.web.common.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tang.
 * @date 2019/12/10.
 */
@Slf4j
@CrossOrigin
public class AuthRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.debug("开始auth拦截");
        if (Operator.getMemberId() == null && Operator.getUserId() == null) {
            throw new PermissionException(ErrorCode.ERROR_COMMON_AUTHORITY.getMsg(), ErrorCode.ERROR_COMMON_AUTHORITY.getCode());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
