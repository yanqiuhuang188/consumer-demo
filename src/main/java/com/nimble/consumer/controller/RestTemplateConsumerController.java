package com.nimble.consumer.controller;


import com.nimble.consumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RestTemplateConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/consume/{id}")
    public User findById(@PathVariable Long id) {
        // provider.url-path: http://localhost:7901/provider/
        System.out.println(this.microServiceNodeInfo());
        return this.restTemplate.getForObject("http://provider/provide/" + id, User.class);
    }

    @GetMapping("/microServiceNodeInfo")
    public String microServiceNodeInfo() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("provider");
        return serviceInstance.toString();
    }

    @RequestMapping("/registered")
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

}
