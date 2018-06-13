package com.nimble.consumer.feignClient;

import com.nimble.consumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider")
public interface UserFeignClient {

    @RequestMapping("provide/{id}")
    User find(@RequestParam("id") Long id);
}
