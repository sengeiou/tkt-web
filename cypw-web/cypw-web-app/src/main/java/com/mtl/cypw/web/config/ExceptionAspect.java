package com.mtl.cypw.web.config;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tang.
 * @date 2019/11/29.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAspect {

    @ExceptionHandler(value = PermissionException.class)
    public TSingleResult permissionExceptionHandler(PermissionException e) {
        return ResultBuilder.failTSingle(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public TSingleResult exceptionHandler(Exception e) {
        log.error("服务异常：{}", e);
        return ResultBuilder.failTSingle(-99, "啊哦，系统开小差了~");
    }

    @ExceptionHandler(value = {Throwable.class})
    public TSingleResult throwableHandler(Exception e) {
        log.error("服务异常：{}", e);
        return ResultBuilder.failTSingle(-99, "啊哦，系统开小差了~");
    }
}
