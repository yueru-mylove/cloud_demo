package com.voiceai.cloud.route.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月20日 17:28:00
 */
@Configuration
public class CustomerZuulConfig {

    private final ZuulProperties zuulProperties;
    private final ServerProperties serverProperties;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerZuulConfig(JdbcTemplate jdbcTemplate, ServerProperties serverProperties, ZuulProperties zuulProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.serverProperties = serverProperties;
        this.zuulProperties = zuulProperties;
    }


    @Bean
    public CustomerRouteLocator customerRouteLocator() {
        CustomerRouteLocator routeLocator = new CustomerRouteLocator(this.serverProperties.getServlet().getServletPrefix(), this.zuulProperties);
        routeLocator.setJdbcTemplate(jdbcTemplate);
        return routeLocator;
    }
}
