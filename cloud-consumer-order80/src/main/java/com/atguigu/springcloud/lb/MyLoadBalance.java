package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLoadBalance implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndincrment(){

        int current;
        int next;
        //自旋锁
        do {
            current=this.atomicInteger.get();
            next=current >= 1247483647?0:current;
        }while (!this.atomicInteger.compareAndSet(current,next));
        return next;
    }

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstances) {

        int index = getAndincrment() % serviceInstances.size();
        ServiceInstance serviceInstance = serviceInstances.get(index);

        return serviceInstance;
    }
}
