package com.miracle.cloud.config.security;

import com.google.common.base.Strings;
import com.miracle.cloud.encrypt.RSAUtil;
import com.miracle.cloud.exception.BusinessException;
import com.miracle.cloud.exception.ErrorCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月15日 17:50:00
 */
public class TokenFilter extends GenericFilterBean {

    private static final String TOKEN_KEY = "token";
    private static final String REDIS_TOKEN_PREFIx = "redis_token_";

    private final RedisTemplate redisTemplate;

    public TokenFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        if (Strings.isNullOrEmpty(header)) {
            throw new BusinessException(ErrorCode.ERROR_LOGIN_CHECK_FAILED.getCode(), ErrorCode.ERROR_LOGIN_CHECK_FAILED.getMsg());
        }
        String s = null;
        try {
            s = RSAUtil.decrypt(RSAUtil.getPrivateKey("123456"), header);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().get(REDIS_TOKEN_PREFIx + s);
    }
}
