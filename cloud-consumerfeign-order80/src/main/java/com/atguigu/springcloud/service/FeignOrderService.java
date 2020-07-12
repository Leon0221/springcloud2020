package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cloud-payment-service")
@Component
public interface FeignOrderService {

    @GetMapping(value = "/payment/feign/timeout")
    CommonResult<String> getPort();
}
