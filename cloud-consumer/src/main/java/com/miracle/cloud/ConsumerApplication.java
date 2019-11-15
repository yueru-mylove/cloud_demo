package com.miracle.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.miracle.cloud.config.UserProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月03日 12:42:00
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
@EnableRabbit
@EnableAsync
public class ConsumerApplication {

    private final RestTemplate restTemplate;
    private final UserProperties userProperties;

    @Autowired
    public ConsumerApplication(RestTemplate restTemplate, UserProperties userProperties) {
        this.restTemplate = restTemplate;
        this.userProperties = userProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


    @HystrixCommand(fallbackMethod = "helloFallback")
    @GetMapping("/hello")
    public String hello() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://trace-messaging/trace/world", String.class);
        return forEntity.getBody() + ":" + userProperties.getName() + ":" + userProperties.getPwd();
    }


    public String helloFallback() {
        return "fallback";
    }


}

