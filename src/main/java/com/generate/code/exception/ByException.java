package com.generate.code.exception;

/**
 * 异常通知处理
 *
 * @author by@Deng
 * @create 2017-09-29 22:09
 */
public class ByException extends RuntimeException{

    public ByException() {}


    /**
     * 返回异常
     * @author by@Deng
     * @date 2017/9/29 下午10:11
     */
    public ByException(String message) {
        super(message);
    }


}
