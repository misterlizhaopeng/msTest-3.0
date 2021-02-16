package com.lp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName com.lp.MsAlibabaNacosClientOrderApplication
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 20:26
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class MsAlibabaNacosClientOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsAlibabaNacosClientOrderApplication.class,args);
    }
}

