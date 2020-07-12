package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalance;
import com.netflix.appinfo.InstanceInfo;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.plugin2.message.GetAppletMessage;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private static final String HTTP_URL = "http://localhost:8001";

    //通过RestTamplte远程调用服务
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private LoadBalance loadBalance;
    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/consumer/payment/add", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public CommonResult add(Payment payment) {
        return restTemplate.postForObject(HTTP_URL + "/payment/add", payment, CommonResult.class);
    }


    @RequestMapping(value = "/consumer/payment/get/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public CommonResult<Payment> getPayment(@PathVariable("id") long id) {

        return restTemplate.getForObject(HTTP_URL + "/payment/get/" + id, CommonResult.class);
    }

    @RequestMapping(value = "/consumer/payment/lb", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String getServiceInstence() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size()<=0){
            return null;
        }
        ServiceInstance instance = loadBalance.getInstance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }



}
