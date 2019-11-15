package com.miracle.cloud.config.security;

import com.google.common.base.Strings;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月14日 14:01:00
 */
public class UrlMatchVoter implements AccessDecisionVoter<Object> {


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof UrlConfigAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (null == authentication) {
            return ACCESS_DENIED;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (!(attribute instanceof UrlConfigAttribute)) {
                continue;
            }
            UrlConfigAttribute attr = (UrlConfigAttribute) attribute;
            for (GrantedAuthority authority : authorities) {
                if (!(authority instanceof UrlGranterAuthority)) {
                    continue;
                }
                UrlGranterAuthority granterAuthority = (UrlGranterAuthority) authority;
                String method = Strings.isNullOrEmpty(granterAuthority.getMethod()) ?
                        granterAuthority.getMethod() : attr.getRequest().getMethod();
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(granterAuthority.getAuthority(), method);
                if (matcher.matches(attr.getRequest())) {
                    return ACCESS_GRANTED;
                }
            }
        }
        return ACCESS_ABSTAIN;
    }
}
