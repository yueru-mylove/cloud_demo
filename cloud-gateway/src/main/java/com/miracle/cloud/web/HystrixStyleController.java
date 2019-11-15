package com.miracle.cloud.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月10日 21:53:00
 */
@RequestMapping("/hystrix")
@RestController
public class HystrixStyleController {




/*    @GetMapping("/async")
    public String async() {
        return new AsyncResult<Object>() {
            @Override
            public Object get() throws ExecutionException {
                return super.get();
            }
        };
    }*/
}
