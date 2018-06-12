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
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    DiscoveryClient discoveryClient;

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

    @RequestMapping("/registered")
    public String getRegistered(){
        List<ServiceInstance> list = discoveryClient.getInstances("PROVIDER");
        System.out.println(discoveryClient.getServices());
        System.out.println("discoveryClient.getServices().size() = " + discoveryClient.getServices().size());

        for( String s :  discoveryClient.getServices()){
            System.out.println("services " + s);
            List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
            for(ServiceInstance si : serviceInstances){
                System.out.println("    services:" + s + ":getHost()=" + si.getHost());
                System.out.println("    services:" + s + ":getPort()=" + si.getPort());
                System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
                System.out.println("    services:" + s + ":getUri()=" + si.getUri());
                System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
            }

        }

        if (list != null && list.size() > 0 ) {
            for(ServiceInstance instance : list){
                System.out.println(instance.getUri());
            }
        }
        return null;
    }

}
