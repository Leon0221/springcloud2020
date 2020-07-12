package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.FeignOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//@RequestMapping
@RestController
public class FeignOrderController {

    @Resource
    private FeignOrderService feignOrderService;

    @RequestMapping(value = "/consumer/feign/port",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public CommonResult getport(){
        CommonResult<String> port = feignOrderService.getPort();
        if (port.getCode().equals("100")) {
            return port;
        } else {
            return null;
        }
    }
}
