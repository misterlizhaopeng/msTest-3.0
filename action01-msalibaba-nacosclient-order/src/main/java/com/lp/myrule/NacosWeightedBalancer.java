package com.lp.myrule;


import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;

/**
 * @ClassName com.lp.myrule.NacosWeightedBalancer
 * @Deacription 根据权重选择随机选择一个
 * @Author LP
 * @Date 2021/2/16 17:22
 * @Version 1.0
 **/
public class NacosWeightedBalancer extends Balancer {
    public static Instance chooseInstanceByRandomWeight(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}

