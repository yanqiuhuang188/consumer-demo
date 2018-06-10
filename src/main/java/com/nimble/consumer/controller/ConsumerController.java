package com.nimble.consumer.controller;


import com.nimble.consumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/consume/{id}")
    public User findById(@PathVariable Long id){
        // provider.url-path: http://localhost:7901/provider/
        System.out.println(this.microServiceNodeInfo());
        return this.restTemplate.getForObject("http://provider/provid/" + id, User.class);
    }

    @GetMapping("/microServiceNodeInfo")
    public String microServiceNodeInfo(){
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("provider");
        return serviceInstance.toString();
    }

}
