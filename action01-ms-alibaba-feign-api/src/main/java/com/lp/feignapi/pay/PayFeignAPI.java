package com.lp.feignapi.pay;

import com.lp.config.PayConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vo.Result;

/**
 * @ClassName com.lp.feignapi.pay.PayFeignAPI
 * @Deacription 支付接口
 * @Author LP
 * @Date 2021/2/17 20:10
 * @Version 1.0
 **/
//name 是被请求的微服务名称，configuration为配置中心，此处配置了日志的级别，是否支持springmvc这样的请求契约，拦截器
@FeignClient(name = "ms-pay",configuration = PayConfig.class)
public interface PayFeignAPI {

    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    Object selectOrderInfoById(@PathVariable("orderId") String orderId);

    @RequestMapping(value = "/pt/{orderId}", method = RequestMethod.GET)
    Result pt(@PathVariable("orderId") String orderId);
}

