package com.miracle.cloud.config.security;

import com.alibaba.fastjson.JSON;
import com.miracle.cloud.bean.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户没有登录时返回给前端的信息
 *
 * @author miracle
 * created at 2019-07-07 17:02
 */
@Component
public class CustomizedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result responseBody = new Result();
        responseBody.setCode(HttpStatus.UNAUTHORIZED.value());
        responseBody.setMsg("Need Authorities!");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}