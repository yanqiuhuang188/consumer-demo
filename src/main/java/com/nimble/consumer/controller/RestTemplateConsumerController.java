package com.nimble.consumer.controller;


import com.nimble.consumer.entity.User;
import com.nimble.consumer.service.RestTemplateConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTemplateConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplateConsumerService restTemplateConsumerService;

    @GetMapping("/consume/{id}")
    public User findById(@PathVariable Long id) {
        return this.restTemplateConsumerService.findById(id);
    }

    @GetMapping("/info")
    public String microServiceNodeInfo() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("provider");
        return serviceInstance.toString();
    }

    @RequestMapping("/registered")
    public void getRegistered() {
        this.restTemplateConsumerService.getRegistered();
    }

}
