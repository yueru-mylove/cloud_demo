package com.voiceai.cloud.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voiceai.cloud.common.bean.Result;
import org.assertj.core.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月05日 15:23:00
 */
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("退出成功");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.close();

    }

    public static void main(String[] args) {
        String s = Strings.escapePercent("$");
        System.out.println(s);

        String dollar = escape("$");
        System.out.println(dollar);
    }

    public static String escape(String keyword) {
        if (!Strings.isNullOrEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\\\" + key);
                }
            }
            return keyword;
        }
        return null;
    }
}