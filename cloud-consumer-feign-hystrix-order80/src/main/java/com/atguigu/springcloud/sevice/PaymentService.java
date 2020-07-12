package com.atguigu.springcloud.sevice;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.sevice.impl.PaymentFallBackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "cloud-provider-hystrix-service",fallback = PaymentFallBackService.class)
public interface PaymentService {

    @RequestMapping(value = "/payment/getHystrixOk/{id}",method = RequestMethod.GET)
     CommonResult getHystrixOk(Integer id);

    @RequestMapping(value = "/payment/getHystrixTimeout/{id}",method = RequestMethod.GET)
     CommonResult getHystrixTimeOut(Integer id);
}
