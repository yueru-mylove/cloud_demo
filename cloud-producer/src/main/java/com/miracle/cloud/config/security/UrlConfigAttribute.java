package com.miracle.cloud.config.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月14日 13:57:00
 */
public class UrlConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest request;

    public UrlConfigAttribute(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
