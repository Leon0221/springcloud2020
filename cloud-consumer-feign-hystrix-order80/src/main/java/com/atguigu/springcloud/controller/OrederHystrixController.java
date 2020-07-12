package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.sevice.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "Global_Exception_FallBackMethod")
public class OrederHystrixController {

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/consumer/payment/getHystrixOk/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public CommonResult getHystrixOk(@PathVariable("id") Integer id){
        CommonResult hystrixOk = paymentService.getHystrixOk(id);
        return new CommonResult(100,"查询成功",hystrixOk);
    }

    @RequestMapping(value = "/consumer/payment/getHystrixTimeout/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
//    @HystrixCommand(fallbackMethod = "getHystrixTimeOutHeader",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000")//熔断的条件
//    })
    @HystrixCommand
    public CommonResult getHystrixTimeOut(@PathVariable("id") Integer id){
        CommonResult hystrixTimeOut = paymentService.getHystrixTimeOut(id);
        return new CommonResult(100,"查询成功",hystrixTimeOut);
    }

    public CommonResult getHystrixTimeOutHeader(Integer id) {
        return new CommonResult(102,"80服务远程调用8001服务超时"+Thread.currentThread().getName());
    }

    //全局的兜底异常处理
    public String Global_Exception_FallBackMethod() {
        return "Global异常处理信息，请稍后再试～～～";
    }
}
