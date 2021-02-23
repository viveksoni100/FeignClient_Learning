package com.example.service1.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author viveksoni100
 */
@RestController
public class Service1Controller {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    public RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping
    public String welcome() {

        ServiceInstance instance = loadBalancerClient.choose("service2");
        URI uri = URI.create(String.format("http://%s:%s/", instance.getHost(), instance.getPort()));

                return "Welcome to Service1, Responce from Service2 (URI : " + uri + ") : "
                        + restTemplate.getForObject("http://localhost:9091/", String.class);
    }
}
