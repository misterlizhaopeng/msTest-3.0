package com.lp.config;

import com.ribbonconfig.GlobalRibbonConfig;
import com.ribbonconfig.PayCenterRibbonConfig;
import com.ribbonconfig.ProductCenterRibbonConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @ClassName com.lp.config.CustomRibbonConfig
 * @Deacription 订单中心需要采用随机算法调用库存中心;而采用轮询算法调用其他中心微服务，其他的继续采用轮询
 * @Author LP
 * @Date 2021/2/16 14:04
 * @Version 1.0
 **/
//@RibbonClients(value = {@RibbonClient(name = "ms-product",configuration = ProductCenterRibbonConfig.class),
//                        @RibbonClient(name = "ms-pay",configuration = PayCenterRibbonConfig.class)},
//               defaultConfiguration = GlobalRibbonConfig.class)
// 测试不同服务采用不同的负载均衡算法时，遇到了这个问题：这个问题的原因是spring容器中存在了两个相同的 bean
// org.springframework.beans.factory.NoUniqueBeanDefinitionException:
//          No qualifying bean of type 'com.netflix.loadbalancer.IRule' available:
//          expected single matching bean but found 2: randomRule,theSameClusterPriorityRule

//@RibbonClients(value = {@RibbonClient(name = "ms-product",configuration = ProductCenterRibbonConfig.class),
//                        @RibbonClient(name = "ms-pay",configuration = PayCenterRibbonConfig.class)})
//@RibbonClient(name = "ms-product",configuration = GlobalRibbonConfig.class)
@RibbonClients(defaultConfiguration = GlobalRibbonConfig.class)
public class CustomRibbonConfig {
}