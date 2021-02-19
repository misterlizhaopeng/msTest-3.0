package com.lp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vo.Result;

import java.util.Objects;

/**
 * @ClassName com.lp.controller.PayController
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/16 14:28
 * @Version 1.0
 **/
@RestController
public class PayController {
    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    public Object selectOrderInfoById(@PathVariable("orderId") String orderId) {
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {
            return "from service=pay,server port is :" + port + ",input id is：" + orderId;
        }
        return "";
    }


    @RequestMapping(value = "/pt/{orderId}", method = RequestMethod.GET)
    public Result pt(@PathVariable("orderId") String orderId) {
        if (!Objects.isNull(orderId) || orderId.isEmpty()) {
            String result="from service=pay,server port is :" + port + ",input id is：" + orderId;
            return Result.success(result);
        }
        return null;
    }
}

