package com.voiceai.cloud.config.security;

import com.voiceai.cloud.constant.ErrorCode;
import com.voiceai.cloud.exception.BusinessException;
import com.voiceai.cloud.utils.RSAUtil;
import org.assertj.core.util.Strings;
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
        String s = RSAUtil.getInstance().decryText(header);
        redisTemplate.opsForValue().get(REDIS_TOKEN_PREFIx + s);
    }
}
