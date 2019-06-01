package com.voiceai.cloud;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月06日 11:32:00
 */
@Data
public class GatewayRouteDefinition {

    private String id;

    /**
     * 配置的过滤规则集合
     */
    private List<GatewayFileterDefinition> fileters = new ArrayList<>();

    /**
     * 配置的断言规则集合
     */
    private List<GatewayPredicationDefinition> predicates = new ArrayList<>();
    /**
     * 路由规则转发的uri
     */
    private String uri;

    /**
     * 路由顺序
     */
    private Integer order = 0;
}
