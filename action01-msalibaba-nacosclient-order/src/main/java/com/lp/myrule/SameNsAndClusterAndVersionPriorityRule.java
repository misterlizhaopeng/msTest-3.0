package com.lp.myrule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName com.lp.myrule.SameNsAndClusterAndVersionPriorityRule
 * @Deacription 同命名空间下，同一个集群优先调用【比如北京的服务之间的调用优先调用本地的，如果本地的被调用的服务不能用，才调用别的区域（这个区域一般称为集群）的服务】，
 *              还要确保金丝雀发布的可靠性（同版本的服务才可以互相调用）【金丝雀发布，同集群也应该优先被调用，不能调用才可以调用别的集群的服务】
 *
 *              金丝雀版本权重负载均衡策略:
 * @Author LP
 * @Date 2021/2/16 17:07
 * @Version 1.0
 **/
@Slf4j
public class SameNsAndClusterAndVersionPriorityRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        try {
            //获取本地所部署集群的名称
            String clusterName = discoveryProperties.getClusterName();
            //去nacos上获取和本地 相同集群   相同版本的所有实例信息
            List<Instance> theSameClusterNameAndTheSameVersionInstList = getTheSameClusterAndTheSameVersionInstances(discoveryProperties);
            //声明被调用的实例
            Instance toBeChooseInstance;
            //判断同集群同版本号的微服务实例是否为空
            if (theSameClusterNameAndTheSameVersionInstList.isEmpty()){
                //跨集群调用相同的版本
                toBeChooseInstance = crossClusterAndTheSameVersionInovke(discoveryProperties);
            }else {
                //具有同集群  同版本号的实例
                toBeChooseInstance = NacosWeightedBalancer.chooseInstanceByRandomWeight(theSameClusterNameAndTheSameVersionInstList);
                log.info("同集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
                        clusterName,toBeChooseInstance.getClusterName(),discoveryProperties.getMetadata().get("current-version"),
                        toBeChooseInstance.getMetadata().get("current-version"),toBeChooseInstance.getIp(),toBeChooseInstance.getPort());
            }

            return new NacosServer(toBeChooseInstance);

        }catch (Exception ex){
            log.error("同集群优先权重负载均衡算法选择异常:{}",ex);
        }

        return null;
    }

    private List<Instance> getTheSameClusterAndTheSameVersionInstances(NacosDiscoveryProperties discoveryProperties) throws NacosException {

        //当前的集群的名称
        String currentClusterName = discoveryProperties.getClusterName();

        //当前的版本号
        String currentVersion = discoveryProperties.getMetadata().get("current-version");

        //获取所有实例的信息(包括不同集群的,不同版本号的)
        List<Instance> allInstance =  getAllInstances(discoveryProperties);

        List<Instance> theSameClusterNameAndTheSameVersionInstList = new ArrayList<>();

        //过滤相同集群  同版本号的实例
        for(Instance instance : allInstance) {
            if(StringUtils.endsWithIgnoreCase(instance.getClusterName(),currentClusterName)&&
                    StringUtils.endsWithIgnoreCase(instance.getMetadata().get("current-version"),currentVersion)) {

                theSameClusterNameAndTheSameVersionInstList.add(instance);
            }
        }

        return theSameClusterNameAndTheSameVersionInstList;
    }


    private List<Instance> getAllInstances(NacosDiscoveryProperties discoveryProperties) throws NacosException {

        //第1步:获取一个负载均衡对象
        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) getLoadBalancer();

        //第2步:获取当前调用的微服务的名称
        String invokedSerivceName = baseLoadBalancer.getName();

        //第3步:获取nacos clinet的服务注册发现组件的api
        NamingService namingService = discoveryProperties.namingServiceInstance();

        //第4步:获取所有的服务实例
        List<Instance> allInstance =  namingService.getAllInstances(invokedSerivceName);

        return allInstance;
    }

    private List<Instance> getCrossClusterAndTheSameVersionInstList(NacosDiscoveryProperties discoveryProperties) throws NacosException {

        //版本号
        String currentVersion = discoveryProperties.getMetadata().get("current-version");

        //被调用的所有实例
        List<Instance> allInstance = getAllInstances(discoveryProperties);

        List<Instance>  crossClusterAndTheSameVersionInstList = new ArrayList<>();

        //过滤相同版本
        for(Instance instance : allInstance) {
            if(StringUtils.endsWithIgnoreCase(instance.getMetadata().get("current-version"),currentVersion)) {

                crossClusterAndTheSameVersionInstList.add(instance);
            }
        }

        return crossClusterAndTheSameVersionInstList;
    }

    private Instance crossClusterAndTheSameVersionInovke(NacosDiscoveryProperties discoveryProperties) throws NacosException {

        //获取所有集群下相同版本的实例信息
        List<Instance>  crossClusterAndTheSameVersionInstList = getCrossClusterAndTheSameVersionInstList(discoveryProperties);
        //当前微服务的版本号
        String currentVersion = discoveryProperties.getMetadata().get("current-version");
        //当前微服务的集群名称
        String currentClusterName = discoveryProperties.getClusterName();

        //声明被调用的实例
        Instance toBeChooseInstance = null ;

        //没有对应相同版本的实例
        if(crossClusterAndTheSameVersionInstList.isEmpty()) {
            log.info("跨集群调用找不到对应合适的版本当前版本为:currentVersion:{}",currentVersion);
            throw new RuntimeException("找不到相同版本的微服务实例");
        }else {
            toBeChooseInstance = NacosWeightedBalancer.chooseInstanceByRandomWeight(crossClusterAndTheSameVersionInstList);

            log.info("跨集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
                    currentClusterName,toBeChooseInstance.getClusterName(),discoveryProperties.getMetadata().get("current-version"),
                    toBeChooseInstance.getMetadata().get("current-version"),toBeChooseInstance.getIp(),toBeChooseInstance.getPort());
        }

        return toBeChooseInstance;
    }




}

