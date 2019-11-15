package com.miracle.cloud.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6401507641198338287L;

    private Integer code;
    private String msg;
    private Object data;

    public BusinessException() {
        super();
    }


    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BusinessException(ErrorCode errorCode, Object data) {
        this(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg + ":" + cause.getMessage();
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(Integer code, String msg, Object data, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg + ":" + cause.getMessage();
        this.data = data;
    }


    public BusinessException(Throwable cause) {
        super(cause);
    }


    public Integer BBgetCode() {
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
