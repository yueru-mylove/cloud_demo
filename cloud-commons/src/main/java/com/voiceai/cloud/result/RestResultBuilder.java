package com.voiceai.cloud.result;

import com.voiceai.cloud.exception.ErrorCode;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 16:21:00
 */
public class RestResultBuilder<T> {

    protected Integer code;

    protected String msg;

    protected T data;

    public static RestResultBuilder builder() {
        return new RestResultBuilder<>();
    }

    public RestResultBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    public RestResultBuilder msg(String msg) {
        this.msg = msg;
        return this;
    }

    public RestResultBuilder data(T data) {
        this.data = data;
        return this;
    }

    public RestResultBuilder errorCode(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        return this;
    }

    public RestResultBuilder success() {
        success(null);
        return this;
    }

    public RestResultBuilder success(T data) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.msg = ErrorCode.SUCCESS.getMsg();
        this.data = data;
        return this;
    }


    public RestResultBuilder failed(Integer code, String msg) {
        failed(code, msg, null);
        return this;
    }


    public RestResultBuilder failed(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public RestResult<T> build() {
        return new RestResult<>(this.code, this.msg, this.data);
    }


    public RestResult build(RestResult restResult) {
        return restResult;
    }
}

