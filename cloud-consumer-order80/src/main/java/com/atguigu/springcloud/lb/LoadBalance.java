package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import javax.validation.constraints.Max;
import java.util.List;

public interface LoadBalance {

    ServiceInstance getInstance(List<ServiceInstance> serviceInstances);
}
