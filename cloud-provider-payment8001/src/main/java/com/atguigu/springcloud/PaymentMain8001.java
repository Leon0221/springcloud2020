package com.atguigu.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PaymentMain8001.class, args);
        System.out.println(run.getEnvironment());
    }
    /**
     * 此配置是为了服务监控而配置，；与服务容错本身没有关系，Spring 层楼的升级后的坑
     * servletRegistrationBean因为springboot的默认路径不是"/hystrix.stream",
     * 只要在自己的项目中配置上 下面的Servlet就可以了
     */
    /*@Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<Servlet> servletServletRegistrationBean = new ServletRegistrationBean<>(hystrixMetricsStreamServlet);
        servletServletRegistrationBean.setLoadOnStartup(1);
        servletServletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletServletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return servletServletRegistrationBean;
    }*/
}
