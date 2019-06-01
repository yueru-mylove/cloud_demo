package com.voiceai.cloud;

import com.voiceai.cloud.service.HelloService;
import com.voiceai.cloud.web.config.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringCloudApplication
@RestController
@EnableScheduling
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    private final HelloService helloService;
    private final UserProperties userProperties;

    @Autowired
    public ProducerApplication(HelloService helloService, UserProperties userProperties) {
        this.helloService = helloService;
        this.userProperties = userProperties;
    }

    @GetMapping("/hello/{str}")
    public String hello(@PathVariable("str") String str) {
        return helloService.hello(str) + userProperties.getName() + "=" + userProperties.getPwd();
    }



    public String fallback() {
        return "fallback";
    }

}
