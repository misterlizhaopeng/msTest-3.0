package com.lp.config;

import com.lp.handler.MappingJackson2HttpMessageConverterHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName com.lp.config.BeanConfig
 * @Deacription 配置类 : 暂时还是不能解决feign调用返回Object类型报错的情况
 * @Author LP
 * @Date 2021/2/17 21:34
 * @Version 1.0
 **/
@Configuration
public class BeanConfig {
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverterHandler());
//        return restTemplate;
//    }

}

