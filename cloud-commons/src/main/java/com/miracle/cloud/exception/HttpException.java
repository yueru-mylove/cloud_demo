package com.miracle.cloud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 17:03:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException{

    private static final long serialVersionUID = 4553476685751332235L;

    /**
     * 异常代码
     */
    protected Integer code;
    /**
     * 异常消息
     */
    protected String message;


    public HttpException() {
        super();
    }

    public HttpException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    public HttpException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message + " : " + cause.getMessage();
    }

    public HttpException(Throwable cause) {
        super(cause);
    }


}
