package com.miracle.cloud;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月06日 11:33:00
 */
@Data
public class GatewayPredicationDefinition {

    /**
     * 断言名
     */
    private String name;

    /**
     * 配置的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
