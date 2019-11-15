package com.miracle.cloud.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.cloud.bean.Result;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月05日 15:22:00
 */
public class CustomizeFailuredHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Result result = new Result();
        result.setCode(401);
        result.setMsg("unauthorized");
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result.setMsg("用户名密码错误");
        } else if (e instanceof DisabledException) {
            result.setMsg("账户被禁用");
        } else {
            result.setMsg("登陆失败，请联系管理员或稍后重试");
        }
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
