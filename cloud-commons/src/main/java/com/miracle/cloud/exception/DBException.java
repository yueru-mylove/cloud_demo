package com.miracle.cloud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 16:55:00
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DBException extends RuntimeException {

    private static final long serialVersionUID = -196315603376058719L;

    protected Integer code;

    protected String message;

    public DBException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message + ":" + cause.getMessage();
    }

    public DBException(Throwable cause) {
        super(cause);
    }


}
