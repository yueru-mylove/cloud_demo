package com.voiceai.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月03日 13:00:00
 */
@Configuration
public class WebConfig {

    /**
     *
     * 只有使用@link {LoadBalanced}的时候才可以使用服务名访问服务，否则将不能通过服务名访问服务
     *
     * @return  RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
