package com.lp.controller;

import com.lp.feignapi.pay.PayFeignAPI;
import com.lp.feignapi.product.ProductFeignAPI;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vo.Result;

/**
 * @ClassName com.lp.controller.OrderController
 * @Deacription
 * @Author LP
 * @Date 2021/2/17 20:36
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @Autowired
    private ProductFeignAPI productFeignAPI;

    @Autowired
    private PayFeignAPI payFeignAPI;

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public Object product(@PathVariable("orderId") String orderId) {
        Object o = productFeignAPI.selectOrderInfoById(orderId);

        return o;
    }

    @RequestMapping(value = "/p2/{orderId}", method = RequestMethod.GET)
    public String product2(@PathVariable("orderId") String orderId) {
        return productFeignAPI.product(orderId);
    }

    @RequestMapping(value = "/pdt/{orderId}", method = RequestMethod.GET)
    public Result pdt(@PathVariable("orderId") String orderId) {
        return productFeignAPI.pdt(orderId);
    }

//    @RequestLine("GET /pdt/{orderId}")
//    public Result pdt(@PathVariable("orderId") String orderId) {
//        return productFeignAPI.pdt(orderId);
//    }


    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.GET)
    public Object pay(@PathVariable("orderId") String orderId) {
        return payFeignAPI.selectOrderInfoById(orderId);
    }

    @RequestMapping(value = "/pt/{orderId}", method = RequestMethod.GET)
    public Object pt(@PathVariable("orderId") String orderId) {
        return payFeignAPI.pt(orderId);
    }
}

