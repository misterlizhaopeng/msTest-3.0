package com.lp.controller;

import com.lp.common.TestCls;
import com.lp.feignapi.pay.PayFeignAPI;
import com.lp.feignapi.product.ProductFeignAPI;
import feign.RequestLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.Result;

/**
 * @ClassName com.lp.controller.OrderController
 * @Deacription
 * @Author LP
 * @Date 2021/2/17 20:36
 * @Version 1.0
 **/
@Slf4j
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

        TestCls testCls = new TestCls();
        String goAction = testCls.testStr("goAction");
        return productFeignAPI.pdt(orderId+":-->"+goAction);
    }

    /**
     * 测试拦截器，这个例子不太好，个人从msTest-2.0的经验得出，应该从拦截器里面获取被拦截的数据，进行操作才可以说明拦截器的作用
     * @param token
     * @return
     */
    @RequestMapping(value = "/inctp", method = RequestMethod.GET)
    Result inctp(@RequestHeader("token") String token){
        return productFeignAPI.inctp(token);
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

