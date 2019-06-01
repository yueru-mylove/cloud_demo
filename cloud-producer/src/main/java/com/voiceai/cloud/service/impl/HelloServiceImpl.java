package com.voiceai.cloud.service.impl;

import com.voiceai.cloud.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月03日 12:56:00
 */
@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String hello(String str) {
        return str + "this is hello service";
    }


}
