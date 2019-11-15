package com.miracle.cloud.exception;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 15:23:00
 */
public enum  ErrorCode {

    /**
     * 成功
     */
    SUCCESS(0, "success"),
    /**
     * 默认捕获异常
     */
    FAILED(-99999, "未知异常，请稍后重试！！"),

    INVALID_PARAM(-1, "参数错误"),

    INVALID_REQUEST_METHOD(-2, "请求方式非法"),

    INVALID_UPLOAD_SIZE(-3, "超出最大上传文件限制！"),

    ERROR_LOGIN_CHECK_FAILED(-4, "登录检查失败！")
    ;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
    }}


