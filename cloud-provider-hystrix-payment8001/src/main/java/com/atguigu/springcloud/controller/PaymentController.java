package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.sevice.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/payment/getHystrixOk/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public CommonResult getHystrixOk(@PathVariable("id") Integer id) {
        String hystrixOk = paymentService.getHystrixOk(id);
        return new CommonResult(100, "查询成功", hystrixOk);
    }

    @RequestMapping(value = "/payment/getHystrixTimeout/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public CommonResult getHystrixTimeOut(@PathVariable("id") Integer id) {
        String hystrixTimeOut = paymentService.getHystrixTimOut(id);
        return new CommonResult(100, "查询成功", hystrixTimeOut);
    }

    /*========服务熔断=========*/

    @RequestMapping(value = "/payment/circuit/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String circuit(@PathVariable("id") Integer id) {

        String circuitBreaker = paymentService.getCircuitBreaker(id);
        return circuitBreaker;
    }
}
