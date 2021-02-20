package com.lp.config;

import com.lp.handler.FeignInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @ClassName com.lp.config.WebConfig
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/20 8:41
 * @Version 1.0
 **/
@Configuration
public class WebConfig {
    /**
     * feign拦截器的作用就是为了在服务之间传递令牌，具体见笔记（13-4-t3-alibaba-微服务之OpenFeign）
     * @return
     */
    @Bean
    public FeignInterceptor selfInterceptor() {
        return new FeignInterceptor();
    }
}

