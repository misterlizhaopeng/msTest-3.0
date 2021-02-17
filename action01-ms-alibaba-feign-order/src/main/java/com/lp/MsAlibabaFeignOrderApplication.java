package com.lp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName com.lp.MsAlibabaFeignOrderApplication
 * @Deacription 测试feign发送请求的 订单微服务
 * @Author LP
 * @Date 2021/2/17 20:32
 * @Version 1.0
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MsAlibabaFeignOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsAlibabaFeignOrderApplication.class,args);
    }
}