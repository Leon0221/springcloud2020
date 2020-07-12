###feign延时
默认Feign客户端只等待一秒钟，但是服务端处理需要超过1秒钟，导致Feign客户端不想等待，
直接返回报错，为了避免这种情况，有时候我们需要设置feign客户端的超时时间。

在yml文件中配置

spring-cloud-starter-openfeign中自己已经集成了spring-cloud-netfilx-ribbon
自己支持负载均衡；因此在设置超时等待时间的时候，可以设置底层的ribbon的超时等待时间
ribbon：#openfeign默认支持ribbon
    ReadTimeout： 5000 #指的是建立连接的所用的时间，适用于网络状况正常的情况下，两端连接的时间
    connectTimeout： 5000 #建立连接后从服务器读取到的可用资源所用的时间
   
###feign 日志打印 监控 输出 
feign提供了日志打印功能，可以调整日志级别，从而了解feign中Http请求的细节
说白了就是**对fringe借口调用的情况进行监控和输出**

### feign 的日志级别
NONE： 默认的，不显示日志
BASIC：仅仅记录请求方法，URL，响应状态码以及执行时间
HEADERS：除了BASIC中的定义的信息之外，还有请求响应头的信息
FULL：除了上面的还有请求和响应的正文以及元数据。

### 日志配置
写配置类 新建config包，包中新建FeignConfig类，类上面加@Configrition注解
，里面编写配置bean，上面加@Bean
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    } 
}

### 在yml中开启日志Bean

logging：
level：
   feign日志以什么级别 监视哪一个接口
   com.atguigu.springcloud.service.xxx: debug
   


