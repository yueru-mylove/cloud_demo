package com.miracle.cloud;

import com.miracle.cloud.listener.RabbitSender;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.miracle.cloud.bean.User;
import com.miracle.cloud.mapper.UserMapper;
import com.miracle.cloud.service.HelloService;
import com.miracle.cloud.web.config.UserProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@MapperScan("com.miracle.cloud.mapper")
@EnableIntegration
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    private final HelloService helloService;
    private final UserProperties userProperties;
    private final UserMapper userMapper;
    private final RabbitSender rabbitSender;

    @Autowired
    public ProducerApplication(HelloService helloService, UserProperties userProperties, UserMapper userMapper, RabbitSender rabbitSender) {
        this.helloService = helloService;
        this.userProperties = userProperties;
        this.userMapper = userMapper;
        this.rabbitSender = rabbitSender;
    }

    @GetMapping("/trace/{str}")
    public String trace(@PathVariable("str") String str) {
        System.out.println(str);
        rabbitSender.sendMessage(str);
        return str;
    }

    @GetMapping("/hello/{str}")
    @PreAuthorize("hasRole('USER')")
    public String hello(@PathVariable("str") String str) {
        rabbitSender.sendMessage(str);
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
