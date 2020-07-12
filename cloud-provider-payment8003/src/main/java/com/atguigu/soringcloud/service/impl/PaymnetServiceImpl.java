package com.atguigu.soringcloud.service.impl;

import com.atguigu.soringcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;

import javax.annotation.Resource;

public class PaymnetServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int add(Payment payment) {
        int add = paymentDao.add(payment);
        return add;
    }

    @Override
    public Payment getPaymentById(Long id) {
        Payment paymentById = paymentDao.getPaymentById(id);

        return paymentById;
    }
}
