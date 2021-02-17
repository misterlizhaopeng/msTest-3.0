package com.lp.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName com.lp.config.PayConfig
 * @Deacription 这个类上千万不要添加@Configuration,不然会被作为全局配置文件共享
 * @Author LP
 * @Date 2021/2/17 20:15
 * @Version 1.0
 **/
public class PayConfig {
    @Bean
    public Logger.Level level(){
        //打印所有日志
        return Logger.Level.FULL;

        //
        //return Logger.Level.HEADERS;
        //return Logger.Level.BASIC;
        //return Logger.Level.NONE;
    }

    /**
     * 根据SpringBoot自动装配 FeignClientsConfiguration 的FeignClient 的契约是SpringMvc
     *
        通过修改契约为默认的Feign的锲约，那么就可以使用默认的注解
     * @return
     */

  /*  @Bean
    public Contract feiContract() {
        return new Contract.Default();
    }*/





}

