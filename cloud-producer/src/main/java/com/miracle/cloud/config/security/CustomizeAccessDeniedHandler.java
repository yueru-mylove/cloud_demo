package com.miracle.cloud.config.security;

import com.alibaba.fastjson.JSON;
import com.miracle.cloud.bean.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月05日 15:20:00
 */
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = httpServletResponse.getWriter();
        Result map = new Result();
        map.setCode(403);
        map.setMsg("权限不足");
        out.write(JSON.toJSONString(map));
        out.flush();
        out.close();
    }
}
