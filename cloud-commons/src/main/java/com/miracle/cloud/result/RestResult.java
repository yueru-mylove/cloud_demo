package com.miracle.cloud.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 16:29:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResult<T> implements Serializable {


    @JSONField(ordinal = 1)
    private Integer code;
    @JSONField(ordinal = 2)
    private String message;
    @JSONField(ordinal = 3)
    private T data;

    public RestResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
