package com.lp.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName com.lp.config.WebConfig
 * @Deacription  配置bean
 * @Author LP
 * @Date 2021/2/14 15:00
 * @Version 1.0
 **/
@Configuration
public class WebConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

