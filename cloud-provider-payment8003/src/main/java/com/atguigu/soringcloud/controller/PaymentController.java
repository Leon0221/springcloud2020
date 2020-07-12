package com.atguigu.soringcloud.controller;

import com.atguigu.soringcloud.service.impl.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;
    @RequestMapping(value = "/payment/add",method = RequestMethod.POST)
    public CommonResult add(@RequestBody Payment payment){
        int add = paymentService.add(payment);
       // Log.info("----插入结果："+add);
        if (add>0){
            //插入成功
            return new CommonResult(200,"插入成功",add);
        }else {
            //插入失败
            return new CommonResult(300,"插入失败",null);

        }
    }

    @RequestMapping(value = "/payment/get/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public CommonResult get(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);

       // Log.info("-----查询结果是： "+paymentById);
        //System.out.println("获得的实体：" + paymentById);
        System.out.println("repezhimeiyoshengxiao");

        if (paymentById != null){
            //有查到
            return new CommonResult(200,"查询成功",paymentById);
        }else {
            //没查到
            return new CommonResult(300,"没有查询到记录，查询ID："+id,null);
        }
    }
    @RequestMapping(value = "/payment/lb",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public String get(){
        return port;
    }
}
