package com.atguigu.springcloud.sevice;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentService {

    /**
     * 正常访问的方法
     * @param id
     * @return
     */
    public String getHystrixOk(Integer id) {
        //当前线程的名字，以及传入的ID
        return "线程池"+Thread.currentThread().getName()+"getHystrixOk,id :" + id+",😄哈哈！！！";
    }

    /**
     * 延迟返回时间方法
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getHystrixTimOutHeader",commandProperties ={
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    } )
    public String getHystrixTimOut(Integer id) {
        long time = 3;
        //当前线程沉睡几秒钟
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()+"getHystrixOk,id :" + id+",😄哈哈！！！我耗时"+time+"秒钟哦";
    }

    public String getHystrixTimOutHeader(Integer id){
        return "服务超时，请稍后再试："+id;
    }

    /*============服务熔断=============*/
    //默认情况下，在10秒内，20次请求次数，失败率达到50%，就会开启熔断，当随着时间的推移，请求次数增加，错误率减少
    // 后，低于50%，熔断将会关闭，服务链路恢复正常情况


    @HystrixCommand(fallbackMethod = "circuitBreakerFallBackMethod",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMillisecodds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorthresholdPercentage",value = "60")//失败率达到多少后开启短路
            //翻译上面的配置：当在10秒内，请求次数10次中，失败率达到百分之60后，就开启短路(在HystrixCommandProperties类中有这些参数的默认值，也可以自己配置)
            //失败次数达到这个熔断条件就熔断打开open，慢慢的看能否达到半开half open 状态，然后过度到close状态
    })
    public String getCircuitBreaker(Integer id) {
        if (id<0) {
            throw new RuntimeException("参数ID不能小于0，ID："+id);
        }
        String s = IdUtil.simpleUUID();
        return "返回线程的名字："+Thread.currentThread().getName()+",id:"+id+",Hutool的uuid是："+s;
    }

    public String circuitBreakerFallBackMethod(Integer id) {
        return "id不能为负数的亲，请重新输入在试试";
    }


}
