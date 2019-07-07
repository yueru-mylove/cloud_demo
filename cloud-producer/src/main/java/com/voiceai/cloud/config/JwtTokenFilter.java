package com.voiceai.cloud.config;

import com.google.common.base.Strings;
import org.apache.tomcat.util.net.SSLImplementation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月06日 17:11:00
 */
public class JwtTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!Strings.isNullOrEmpty(request.getHeader("token"))) {
            SimpleGrantedAuthority roleGuest = new SimpleGrantedAuthority("ROLE_GUEST");
            SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
            SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(roleAdmin);
            authorities.add(roleGuest);
            authorities.add(roleUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken("zhangsan", "cxt301===", authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
