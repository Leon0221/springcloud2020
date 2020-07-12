package com.atguigu.springcloud.sevice.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.sevice.PaymentService;

public class PaymentFallBackService implements PaymentService {
    @Override
    public CommonResult getHystrixOk(Integer id) {
        return new CommonResult(102,"fuwuyouwenti");
    }

    @Override
    public CommonResult getHystrixTimeOut(Integer id) {
        return new CommonResult(102,"fuwuchaoshi ");
    }
}
