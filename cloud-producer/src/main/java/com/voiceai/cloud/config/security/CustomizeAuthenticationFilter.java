package com.voiceai.cloud.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月15日 15:23:00
 */
public class CustomizeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public CustomizeAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler,
                                         AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = null;
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType()) || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            try {
                ServletInputStream inputStream = request.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                AuthenticationBean bean = mapper.readValue(inputStream, AuthenticationBean.class);
                token = new UsernamePasswordAuthenticationToken(bean.getUsername(), bean.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
                token = new UsernamePasswordAuthenticationToken("", "");

            }
        } else {
            return super.attemptAuthentication(request, response);
        }
        return this.authenticationManager.authenticate(token);
    }
}
