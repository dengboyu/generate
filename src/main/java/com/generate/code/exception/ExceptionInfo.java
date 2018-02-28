package com.generate.code.exception;

/**
 * 返回错误信息
 *
 * @author by@Deng
 * @create 2017-09-29 23:00
 */
public class ExceptionInfo {
    private String exception;

    public ExceptionInfo(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
