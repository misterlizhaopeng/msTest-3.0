package com.lp.config;

import feign.Contract;
import feign.Logger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;

/**
 * @ClassName com.lp.config.ProductConfig
 * @Deacription 这个类上千万不要添加@Configuration,不然会被作为全局配置文件共享
 * @Author LP
 * @Date 2021/2/17 20:23
 * @Version 1.0
 **/
public class ProductConfig {
    @Bean
    public Logger.Level level(){
        //打印所有日志
        return Logger.Level.FULL;//这种情况，在生成上不能使用，日志量太大

        //
        //return Logger.Level.HEADERS;
        //return Logger.Level.BASIC; 生成上最多一般建议用此种feign的日志级别
        //return Logger.Level.NONE;
    }

    /**
     * 根据SpringBoot自动装配 FeignClientsConfiguration 的 FeignClient 的契约是 SpringMvc

        通过修改契约为默认的Feign的锲约，那么就可以使用默认的注解
     * @return
     */
//    @Bean
//    public Contract feiContract() {
//        return new Contract.Default();
//    }


}

