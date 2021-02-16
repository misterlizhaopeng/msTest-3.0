package com.lp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @ClassName com.lp.controller.ProductController
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 21:57
 * @Version 1.0
 **/
@RestController
public class ProductController {
    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    public Object selectOrderInfoById(@PathVariable("orderId") String orderId) {
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {
            return "from service=product,server port is :" + port + ",input id is：" + orderId;
        }
        return "";
    }

}

