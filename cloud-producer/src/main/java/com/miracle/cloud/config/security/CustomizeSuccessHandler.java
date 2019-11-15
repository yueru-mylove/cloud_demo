package com.miracle.cloud.config.security;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.cloud.bean.Result;
import com.miracle.cloud.encrypt.RSAUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月05日 15:19:00
 */
public class CustomizeSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String USERNAME = "username";
    private static final String ID = "id";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        RoledUserDetail userDetail = (RoledUserDetail) authentication.getPrincipal();
        TokenAuthBean bean = new TokenAuthBean();
        bean.setUsername(userDetail.getUsername());
        bean.setId(userDetail.getId());
        String token = null;
        try {
            token = RSAUtil.encrypt(RSAUtil.getPublicKey("123456"), JSON.toJSONString(bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(token);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
