package com.lp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * @ClassName com.lp.config.SelfDefineRestTemplate
 * @Deacription 根据resttemplate自定义负载均衡器策略，
 * 这里值得注意的是：调用SelfDefineRestTemplate，传进来的url为指定的服务名称!!!!
 * 还有，这是一个负载均衡，在被使用的时候，不需要添加@LoadBalance
 * @Author LP
 * @Date 2021/2/16 12:09
 * @Version 1.0
 **/
@Slf4j
public class SelfDefineRestTemplate extends RestTemplate {
    private DiscoveryClient discoveryClient;
    public SelfDefineRestTemplate(DiscoveryClient discoveryClient){this.discoveryClient=discoveryClient;}

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;
        try {

            log.info("请求的url路径为:{}",url);
            //把服务名 替换成我们的IP
            url = replaceUrl(url);

            log.info("替换后的路径:{}",url);

            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            return (responseExtractor != null ? responseExtractor.extractData(response) : null);
        }
        catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new ResourceAccessException("I/O error on " + method.name() +
                    " request for \"" + resource + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 把微服务名称  去注册中心拉取对应IP进行调用
     * @param url
     * @return
     */
    private URI replaceUrl(URI url){
        //1:从URI中解析调用的调用的serviceName=product-center
        String serviceName = url.getHost();
        log.info("调用微服务的名称:{}",serviceName);

        //2:解析我们的请求路径 reqPath= /selectProductInfoById/1
        String reqPath = url.getPath();
        log.info("请求path:{}",reqPath);


        //通过微服务的名称去nacos服务端获取 对应的实例列表
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
        if(serviceInstanceList.isEmpty()) {
            throw new RuntimeException("没有可用的微服务实例列表:"+serviceName);
        }

        String serviceIp = chooseTargetIp(serviceInstanceList);

        String source = serviceIp+reqPath;
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            log.error("根据source:{}构建URI异常",source);
        }
        return url;
    }

    /**
     * 从服务列表中 随机选举一个ip
     * @param serviceInstanceList
     * @return
     */
    private String chooseTargetIp(List<ServiceInstance> serviceInstanceList) {
        //采取随机的获取一个
        Random random = new Random();
        Integer randomIndex = random.nextInt(serviceInstanceList.size());
        String serviceIp = serviceInstanceList.get(randomIndex).getUri().toString();
        log.info("随机选举的服务IP:{}",serviceIp);
        return serviceIp;
    }
}

