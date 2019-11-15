package com.miracle.cloud.service.impl;

import com.miracle.cloud.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 09:24:00
 */
@Service
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean hasPermission = false;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (null != authorities && !authorities.isEmpty()) {
                for (GrantedAuthority auth : authorities) {
                    if (matcher.match(auth.getAuthority(), request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }
}
