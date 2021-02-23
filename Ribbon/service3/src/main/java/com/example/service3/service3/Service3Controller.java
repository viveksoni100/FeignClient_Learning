package com.example.service3.service3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author viveksoni100
 */
@RestController
@RibbonClient(name = "service2", configuration = RibbonConfiguration.class)
public class Service3Controller {

    @Autowired
    public RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping
    public String welcome() {

        return "Welcome to Service3, Responce from Service2 :"
                + restTemplate.getForObject("http://service2", String.class);
    }
}
