package com.mightyjava;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author viveksoni100
 */
@FeignClient(value = "${feign.name}", url = "${feign.url}", fallback = MyFeignClientFallbackImpl.class)
public interface MyFeignClient {

    @GetMapping
    String client3Response();
}
