package com.atguigu.soringcloud.service.impl;

import com.atguigu.springcloud.entities.Payment;

public interface PaymentService {
    public int add(Payment payment);

    public Payment getPaymentById(Long id);
}
