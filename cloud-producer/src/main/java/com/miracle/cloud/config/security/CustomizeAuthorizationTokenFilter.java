package com.miracle.cloud.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月07日 18:37:00
 */
@Component
public class CustomizeAuthorizationTokenFilter extends GenericFilterBean {

    private static final String TOKEN = "token";
    private static final String REDIS_PREFIX = "";

    /*private final RedisTemplate redisTemplate;
    private final MongoTemplate mongoTemplate;

    public CustomizeAuthorizationTokenFilter(RedisTemplate redisTemplate, MongoTemplate mongoTemplate) {
        this.redisTemplate = redisTemplate;
        this.mongoTemplate = mongoTemplate;
    }*/

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
/*        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String token = request.getHeader(TOKEN);
            if (!Strings.isNullOrEmpty(token)) {
                if (redisTemplate.opsForValue().get(token) == null) {
                    throw new BusinessException(ErrorCode.ERROR_LOGIN_CHECK_FAILED.getCode(), ErrorCode.ERROR_LOGIN_CHECK_FAILED.getMsg());
                }
                TokenAuthBean bean = JSON.parseObject(RSAUtil.getInstance().decryText(TOKEN), TokenAuthBean.class);
                if (null == bean) {
                    throw new BusinessException(ErrorCode.ERROR_LOGIN_CHECK_FAILED.getCode(), ErrorCode.ERROR_LOGIN_CHECK_FAILED.getMsg());
                }
                DevUser one = mongoTemplate.findOne(Query.query(Criteria.where(TableFieldConstant.UID).is(bean.getUsername())
                        .andOperator(Criteria.where(TableFieldConstant.COMMON_ID).is(bean.getId()))), DevUser.class, TableNameConstant.TBL_DEV_USER);
                if (null == one) {
                    throw new BusinessException(ErrorCode.ERROR_LOGIN_CHECK_FAILED.getCode(), ErrorCode.ERROR_LOGIN_CHECK_FAILED.getMsg());
                }
                // redisTemplate.opsForValue().set();
                Set<SimpleGrantedAuthority> authorities = one.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(one.getUid(), one.getPwd(), authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }*/
    }

}
