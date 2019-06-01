package com.voiceai.cloud.exception;

import lombok.Data;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 15:21:00
 */
@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 6401507641198338287L;

    private Integer code;
    private String msg;
    private Object data;

    public ServiceException() {
        super();
    }

    public ServiceException(Integer code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ServiceException(ErrorCode errorCode, Object data) {
        this(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public ServiceException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg + ":" + cause.getMessage();
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ServiceException(Integer code, String msg, Object data, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg + ":" + cause.getMessage();
        this.data = data;
    }


    public ServiceException(Throwable cause) {
        super(cause);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
