package com.young.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.ZuulFilterInitializer;
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
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.gateway.filter
 * @ClassName ProducerFallback
 * @Description
 * @Author young
 * @Modify young
 * @Dae 2020/3/8 20:50
 * @Version 1.0.0
 **/
@Slf4j
@Component
public class ProducerFallback  implements FallbackProvider {

    //指定要处理的 服务。
    @Override
    public String getRoute() {
        return "spring-cloud-producer";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            log.info("Exception , {}",reason);
        }
        return getClientHttpResponse();
    }

    private ClientHttpResponse getClientHttpResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() {
                return 200;
            }

            @Override
            public String getStatusText() {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("The service is unavailable.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

}
