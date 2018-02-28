package com.generate.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义处理异常
 *
 * @author by@Deng
 * @create 2017-09-29 22:12
 */
@ControllerAdvice
public class ByExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ByException.class)
    @ResponseBody
    public ExceptionInfo handleBadRequest(ByException be) {
        return new ExceptionInfo(be.getMessage());
    }

}
