网关是什么，为什么要用网关？
spring boot 2.0XXX 和Spring webflux 和
网关是一种理念，具体实现有Netflix的zuul,已经Spring cloud Gateway；
Gateway是Spring生态系统之上构建的API网关服务，基于Spring5 Springboot2和project reactor 和Spring webflux 等，
旨在提供一种简单而有效的方式来对API进行路由，以及提供强大的过滤功能，例如熔断，限流，重试等；
他作为springcloud生态系统的网关，目标是替代zuul,在Springboot2。0以上宾没有对zuul进行集成。为了提升网关性能，SpringcloudGateway是基于webflux框架
实现的，而webflux框架底层则使用了高性能的reactor模式通信框架netty。
（哔哩哔哩中 韩顺平老师讲的netty要看看，周阳老师的 大场面试题第二季）
 
 异步非阻塞

特性：
基于Spring 5 project reactor和Springboot2。0进行构建；
动态路由：能够匹配任何请求属性
可以对路由制定断言和过滤
集成了hystrix的断路器功能
集成了Spring cloud服务发现功能
易于编写断言和过滤
请求限流功能
支持路径重新写
https://www.bilibili.com/video/BV18E411x7eT?p=72

filter 主要实现 GlobalFilter, Ordered 这两个接口