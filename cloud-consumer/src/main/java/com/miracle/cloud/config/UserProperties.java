package com.miracle.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月06日 16:24:00
 */
@Component
@RefreshScope
@Data
public class UserProperties {

    @Value("${user.name}")
    private String name;

    @Value("${user.pwd}")
    private String pwd;




}
