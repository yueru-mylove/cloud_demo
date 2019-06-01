package com.voiceai.cloud.fallback;

import com.alibaba.fastjson.JSON;
import com.voiceai.cloud.exception.DBException;
import com.voiceai.cloud.exception.ErrorCode;
import com.voiceai.cloud.exception.HttpException;
import com.voiceai.cloud.exception.ServiceException;
import com.voiceai.cloud.result.RestResult;
import com.voiceai.cloud.result.RestResultBuilder;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务熔断设置
 *
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月10日 15:56:00
 */
@Component
public class ZuulFallback implements FallbackProvider {

    /**
     * 指定服务名称，*指定所有服务
     *
     * @return  String
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                RestResult result = null;
                if (cause instanceof ServiceException) {
                    ServiceException se = (ServiceException) cause;
                    result = RestResultBuilder.builder().code(se.getCode()).msg(se.getMsg()).build();
                } else if (cause instanceof DBException) {
                    DBException de = (DBException) cause;
                    result = RestResultBuilder.builder().code(de.getCode()).msg(de.getMessage()).build();
                } else if (cause instanceof HttpException) {
                    HttpException he = (HttpException) cause;
                    result = RestResultBuilder.builder().code(he.getCode()).msg(he.getMessage()).build();
                } else {
                    result = RestResultBuilder.builder().code(ErrorCode.FAILED.getCode()).msg(ErrorCode.FAILED.getMsg()).build();
                }
                return new ByteArrayInputStream(JSON.toJSONString(result).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }


}
