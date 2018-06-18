package com.nimble.consumer.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nimble.consumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestTemplateConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    public void getRegistered() {
        List<String> serviceList = discoveryClient.getServices();
        for (String service : serviceList) {
            System.out.println("services " + service);
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(service);
            for (ServiceInstance serviceInstance : serviceInstances) {
                System.out.println("    services:" + service + ":getHost()=" + serviceInstance.getHost());
                System.out.println("    services:" + service + ":getPort()=" + serviceInstance.getPort());
                System.out.println("    services:" + service + ":getServiceId()=" + serviceInstance.getServiceId());
                System.out.println("    services:" + service + ":getUri()=" + serviceInstance.getUri());
                System.out.println("    services:" + service + ":getMetadata()=" + serviceInstance.getMetadata());
            }
        }
    }

    @HystrixCommand(fallbackMethod = "providerFallback")
    public User findById(Long id) {
        // provider.url-path: http://localhost:7901/provider/
        return this.restTemplate.getForObject("http://provider/provide/" + id, User.class);
    }

    // TODO 这里有个问题，直接调用服务是可以抛出fallback对象的，但是通过zuul网关调用就会抛出错误。
    public User providerFallback(Long id) {
        System.out.println("异常发生（RestTemplate方式），进入fallback方法，接收的参数：id = " + id);
        User user = new User();
        user.setId(-1L);
        user.setUserName("user_default");
        user.setName("default");
        user.setAge(0);
        user.setBalance(new BigDecimal(0));
        return user;
    }
}
