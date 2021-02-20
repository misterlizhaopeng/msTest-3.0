package com.lp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import vo.Result;

import java.util.Objects;

/**
 * @ClassName com.lp.controller.ProductController
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 21:57
 * @Version 1.0
 **/
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    public Object selectOrderInfoById(@PathVariable("orderId") String orderId) {
        Object obj=null;
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {

            obj= "from service=product,server port is :" + port + ",input id is：" + orderId;

        }
        return obj;
    }

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public String product(@PathVariable("orderId") String orderId) {
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {
            return "from service=product,server port is :" + port + ",input id is：" + orderId;
        }
        return "";
    }

    @RequestMapping(value = "/pdt/{orderId}", method = RequestMethod.GET)
    public Result pdt(@PathVariable("orderId") String orderId) {
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {
            return  Result.success("from service=product,server port is :" + port + ",input id is：" + orderId);
        }
        return Result.fail();
    }

    /**
     * 测试拦截器，这个例子不太好，个人从msTest-2.0的经验得出，应该从拦截器里面获取被拦截的数据，进行操作才可以说明拦截器的作用
     * @param token
     * @return
     */
    @RequestMapping(value = "/inctp", method = RequestMethod.GET)
    public Result inctp(@RequestHeader("token") String token) {
        if (!Objects.isNull(token) || token.isEmpty()) {
            return  Result.success("from service=product,server port is :" + port + ",input token is：" + token);
        }
        return Result.fail();
    }
}