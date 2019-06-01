package com.voiceai.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月03日 11:26:00
 */
@EnableEurekaServer
@SpringBootApplication
public class RegisterServer {

    public static void main(String[] args) {
        SpringApplication.run(RegisterServer.class, args);
    }
}
