package com.lp.feignapi.product;

import com.lp.config.ProductConfig;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vo.Result;

/**
 * @ClassName com.lp.feignapi.product.ProductFeignAPI
 * @Deacription 产品接口
 * @Author LP
 * @Date 2021/2/17 20:09
 * @Version 1.0
 **/
//name 是被请求的微服务名称，configuration为配置中心，此处配置了日志的级别，是否支持springmvc这样的请求契约，拦截器
@FeignClient(name = "ms-product")  //,configuration = ProductConfig.class
public interface ProductFeignAPI {

    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    Object selectOrderInfoById(@PathVariable("orderId") String orderId);

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    String product(@PathVariable("orderId") String orderId);

    @RequestMapping(value = "/pdt/{orderId}", method = RequestMethod.GET)
    Result pdt(@PathVariable("orderId") String orderId);

//    @RequestLine("GET /pdt/{orderId}")
//    Result pdt(@PathVariable("orderId") String orderId);

    @RequestMapping(value = "/inctp", method = RequestMethod.GET)
    Result inctp(@RequestHeader("token") String token);
}

