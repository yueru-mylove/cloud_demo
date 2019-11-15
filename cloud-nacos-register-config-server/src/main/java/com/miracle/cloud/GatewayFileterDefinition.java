package com.miracle.cloud;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月06日 11:35:00
 */
@Data
public class GatewayFileterDefinition {

    /**
     * 过滤名
     */
    private String name;
    /**
     * 对应的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
