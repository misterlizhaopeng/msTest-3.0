package com.lp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName com.lp.MsAlibabaNacosClientProductApplication
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 21:56
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class MsAlibabaNacosClientProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsAlibabaNacosClientProductApplication.class,args);
    }
}

