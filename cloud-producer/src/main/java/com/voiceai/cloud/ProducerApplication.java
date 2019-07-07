package com.voiceai.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.voiceai.cloud.bean.User;
import com.voiceai.cloud.mapper.UserMapper;
import com.voiceai.cloud.service.HelloService;
import com.voiceai.cloud.web.config.UserProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年0
 */
@EnableDiscoveryClient
@SpringBootApplication
@SpringCloudApplication
@RestController
@EnableScheduling
@MapperScan("com.voiceai.cloud.mapper")
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    private final HelloService helloService;
    private final UserProperties userProperties;
    private final UserMapper userMapper;

    @Autowired
    public ProducerApplication(HelloService helloService, UserProperties userProperties, UserMapper userMapper) {
        this.helloService = helloService;
        this.userProperties = userProperties;
        this.userMapper = userMapper;
    }

    @GetMapping("/hello/{str}")
    @PreAuthorize("hasRole('USER')")
    public String hello(@PathVariable("str") String str) {
        return helloService.hello(str) + userProperties.getName() + "=" + userProperties.getPwd();
    }


    @GetMapping("/admin/{str}")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(@PathVariable("str") String str) {
        return helloService.hello(str) + userProperties.getName() + "=" + userProperties.getPwd();
    }


    @GetMapping("/guest/{str}")
    @PreAuthorize(value = "hasRole('GUEST')")
    public String guest(@PathVariable("str") String str) {
        return helloService.hello(str) + userProperties.getName() + "=" + userProperties.getPwd();
    }

    @GetMapping("/user/{id}")
    @HystrixCommand(fallbackMethod = "fallback", commandKey = "")
    public User getUser(@PathVariable("id") String id) {
        return userMapper.selectById(id);
    }



    public User fallback(String string) {
        return new User();
    }

}
