package com.ribbonconfig;

import com.lp.myrule.NacosWeightRule;
import com.lp.myrule.SameNsAndClusterAndVersionPriorityRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalRibbonConfig {

    @Bean
    public IRule theSameClusterPriorityRule() {
        //return new RoundRobinRule();
        //return new NacosWeightRule();
        return new SameNsAndClusterAndVersionPriorityRule();
    }
}
