package com.miracle.cloud.route.zuul;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月13日 21:43:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class ZuulRoute {


    private String id;

    private String path;

    private String serviceId;

    private String url;

    private boolean stripPrefix = true;

    private boolean retryable = true;

    /**
     * 不传递到下游请求的敏感标头列表。默认为“安全”的头集，通常包含用户凭证。如果下游服务是与代理相同的系统的一
     * 部分，那么将它们从列表中删除就可以了，因此它们共享身份验证数据。如果在自己的域之外使用物理URL，那么通常来
     * 说泄露用户凭证是一个坏主意
     */
    private Set<String> sensitiveHeaders = new LinkedHashSet<>();

    /**
     * 是否生效，默认不生效
     */
    private boolean customSensitiveHeader;


}
