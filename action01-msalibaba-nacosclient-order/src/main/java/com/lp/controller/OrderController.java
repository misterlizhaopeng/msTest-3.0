package com.lp.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName com.lp.controller.OrderController
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/13 21:15
 * @Version 1.0
 **/
@RestController
public class OrderController implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hh/{input}")
    public String test(@PathVariable("input") String input) {
        return "return:" + input;
    }

    /**
     * 从nacos注册中心获取服务的实例信息
     *
     * @return
     */
    @GetMapping("/getServerList")
    public List<ServiceInstance> getServices() {
        List<ServiceInstance> instances = discoveryClient.getInstances("ms-order");
        return instances;
    }

    /**
     * 测试nacos的命名空间的作用(使不同命名空间之间的服务不能互相调用)：order服务调用product服务，测试order服务和product服务分别在同一个命名空间和不在同一个命名空间的场景下，这两种情况；
     *
     * @param orderId 订单id
     * @return
     */
    @RequestMapping(value = "/selectOrderInfoById/{orderId}", method = RequestMethod.GET)
    public Object selectOrderInfoById(@PathVariable("orderId") String orderId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("ms-product");
        if (Objects.isNull(instances) || instances.isEmpty()) {
            return "产品微服务没有可用的实例";
        }
        String uri = instances.get(0).getUri().toString();
        String reqUrl = uri + "/selectOrderInfoById/" + orderId;
        System.out.println(reqUrl);
        //发现1： 断点停到此位置之后，nacos注册中心的当前服务被踢出了，是不是当前线程和心跳线程是同一个线程还是 ？
        return restTemplate.getForObject(reqUrl, String.class);
    }


    /**
     * 测试restTemplate 加上@lb注解之后的负载均衡，注意：此时url里面为服务名称；
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public Object product(@PathVariable("orderId") String orderId) {
        String reqUrl =  "http://ms-product/selectOrderInfoById/" + orderId;
        System.out.println(reqUrl);
        //发现1： 断点停到此位置之后，nacos注册中心的当前服务被踢出了，是不是当前线程和心跳线程是同一个线程还是 ？
        return restTemplate.getForObject(reqUrl, String.class);
    }

    /**
     * 测试restTemplate 加上@lb注解之后的负载均衡，注意：此时url里面为服务名称；
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.GET)
    public Object pay(@PathVariable("orderId") String orderId) {
        String reqUrl =  "http://ms-pay/selectOrderInfoById/" + orderId;
        System.out.println(reqUrl);
        //发现1： 断点停到此位置之后，nacos注册中心的当前服务被踢出了，是不是当前线程和心跳线程是同一个线程还是 ？
        return restTemplate.getForObject(reqUrl, String.class);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

