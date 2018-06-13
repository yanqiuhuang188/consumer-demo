package com.nimble.consumer.controller;


import com.nimble.consumer.entity.User;
import com.nimble.consumer.feignClient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignConsumerController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("find/{id}")
    public User find(@PathVariable Long id){
        User user = this.userFeignClient.find(id);
        return user;
    }

}
