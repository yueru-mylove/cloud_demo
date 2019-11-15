package com.miracle.cloud.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月14日 11:42:00
 */
public class UrlGranterAuthority implements GrantedAuthority {

    private final String method;
    private final String url;

    public UrlGranterAuthority(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getAuthority() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        UrlGranterAuthority target = (UrlGranterAuthority) obj;
        if (method.equals(target.getMethod()) && url.equals(target.getUrl())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        return  31 * result + (url != null ? url.hashCode() : 0);
    }

    public static void main(String[] args) {
        String s = "$2a$10$fXoylLsYPQtAjk//8c1H9eEf8Z3vcXQ.LLrezDk0VN2r0ptUMka92";
        String mirAcle_ = new BCryptPasswordEncoder().encode("wwj5525@126.com");
        System.out.println(mirAcle_);
    }
}
