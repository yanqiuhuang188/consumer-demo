package com.nimble.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Ribbon的负载均衡
 *
 */
@Configuration
public class RibbonConfig {

    @Bean
    @ConditionalOnMissingBean
    public IRule ribbonRule() {
        // 随机负载
        return new RandomRule();
    }

}