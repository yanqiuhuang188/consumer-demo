package com.nimble.consumer.feignClient;

import com.nimble.consumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;


@FeignClient(name = "provider", fallback = ProviderFallBack.class)
public interface ProviderFeignClient {

    @RequestMapping("provide/{id}")
    User find(@RequestParam("id") Long id);

}


@Component
class ProviderFallBack implements ProviderFeignClient {
    @Override
    public User find(Long id) {
        System.out.println("异常发生（FeignClient方式），进入fallback方法，接收的参数：id = " + id);
        User user = new User();
        user.setId(-1L);
        user.setUserName("user_default");
        user.setName("default");
        user.setAge(0);
        user.setBalance(new BigDecimal(0));
        return user;
    }
}