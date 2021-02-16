package com.mightyjava;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class Application {

    @Autowired
    private MyFeignClient myFeignClient;

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "homeFallBack")
    public String home() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Hello World");
        jsonObject.put("message-2", new JSONObject(myFeignClient.client2Response()));
        return jsonObject.toString();
    }

    public String homeFallBack() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Hello World");
        jsonObject.put("message-2", "Server 8002 is down!");
        return jsonObject.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
