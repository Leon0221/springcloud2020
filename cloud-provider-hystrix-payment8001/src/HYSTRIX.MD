#hystrix断路器
##概述
###分布式系 统面临的问题
复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系某些时候将不可避免的失败
服务雪崩
多个微服务之间调用的时候，A调用B，B调用C，C有调用其他的服务，这就是所谓的
"扇出"；
如果扇出的链路上的某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，
进而引起系统崩坏，即所谓的雪崩效应；等等一系列情况。

###Hystrix
它是一个用于处理分布式系统的延迟和容错的开源库，在分布式里，许多依赖不可避免的你调用失败，比如超时，异常等，
Hystrix能够保证在一个依赖出问题的情况下，**不会导致
整体的服务失败，避免级联故障，以提高分布式系统的弹性**；
它本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似于熔断保险丝）
，向调用方返回一个符合预期的可以处理的备选响应（FallBack），而不是长时间的等待或者抛出无法处理的异常，
这样就保证了服务调用方的线程不会被长时间，不必要的占用，从而避免了故障在分布式系统中蔓延，乃至雪崩。
### Hystrix 能做什么 官网：http://github.com/Netflix/Hystrix/wiki/How-To-Use
服务降级
服务熔断
接近实时的监控
限流等
### Hystrix目前状态，停止更新，进入维护模式

## Hystrix重要概念
###服务降级
对方系统不可用了，要给我一个友好提示，返回一个预期的可执行的兜底的方案 FallBack；
程序运行异常，超时，线程池满了等

###服务熔断
类似于保险丝达到最大的访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法，返回友好提示
服务降级》服务熔断呢》慢慢恢复链路服务
###服务限流
秒杀高并发等操作，严谨一窝蜂拥挤进来，大家排队，一秒钟限制通过N个，有序进行

## Hystrix案例

jmeter压力测试（未学习）

###由于高并发的产生，使原有的服务变慢，甚至报错，如何解决
超时导致服务器变慢（转圈圈），则超时不再等待
出错（宕机或者程序出错），则出错要有兜底的
####解决
对方服务（8001）超时了，调用者（80）不能一直卡死，必须要有服务降级；
对方服务（8001）down机了，调用者（80）不能一直卡死等待，必须要有降级；
对方服务（8001）OK，调用者（80）自己出故障或者有自我要求（自己的等待时间小于服务器的提供有效数据的时间），自己处理降级。


###降级配置
@HystrixCommand
先从服务提供者8001找原因，设置自身调用的超时时间的峰值，峰值内可以正常运行，超过了有兜底方法处理，作服务降级fallback

8001fallback：
    业务类启用，@HystrixCommand报异常后处理：
        一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中制定的方法
       @HystrixCommand(fallbackMethod = "getHystrixTimOutHeader",commandProperties ={
                   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
           } )
           public String getHystrixTimOut(Integer id) {
           '''主业务'''
           }
           //熔断以后返回的业务
           public String getHystrixTimOutHeader(Integer id){
                   return "服务超时，请稍后再试："+id;
               }
           当服务不可用的时候，做服务降级，兜底的方案就是getHystrixTimOutHeader这个。
       
    主启动类激活：
        添加新注解：@EnableCircuitBreaker

80fallback 消费端：
    业务类方法上加@HystrixCommand，和上面服务提供者的一样
    yml文件中配置：feign:
                     hystrix:
                         enabled: true
    主启动类加：@EnableHystrix 注解
    


###上面的服务提供者和消费者都是用了熔断，但是存在问题
1）：每个业务方法对应一个兜底的方法，代码膨胀；
2）：兜底方法和业务代码混在一个，侵入了业务代码；
3）：统一和自定义的要分开。
### 怎么解决
1）：每个方法配置一个？？？膨胀
            1:feign接口系列
            2:@DefaultProperties(DefaultFallBack="")
                    一对一每个方法配置一个服务降级方法，技术上可以，但是不好，一对多除了个别重要的核心业务专属，其他的普通的都可以通过
                    @DefaultProperties（defalutFallBack=" "）同一跳转到统一的处理页面
              通用的和独享的各自分开，避免代码膨胀，减少代码量
           
            @DefaultProperties(defaultFallback = "Global_Exception_FallBackMethod")
            public class OrederHystrixController {
             @RequestMapping(value = "/consumer/payment/getHystrixTimeout/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
            //    @HystrixCommand(fallbackMethod = "getHystrixTimeOutHeader",commandProperties = {
            //            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000")//熔断的条件
            //    })
                @HystrixCommand
                public CommonResult getHystrixTimeOut(@PathVariable("id") Integer id){
                    ...
                }
                //全局的兜底异常处理
                public String Global_Exception_FallBackMethod() {
                    return "Global异常处理信息，请稍后再试～～～";
                }
            }
           加 @DefaultProperties(defaultFallback = ""）之后如果在方法上还加了@HystrixCommand，并配置了fallback方法，则找自己的，如没有则找全局的。

            3:controller配置

###服务熔断
类似于保险丝
####熔断机制的概述
熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某一个微服务出错不可用或者响应时间太长时，会进行服务降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。
**当检测到该节点微服务调用响应正常（恢复正常）后，恢复调用链路**。
在springcloud框架中，熔断机制通过Hystrix实现。Hystrix会监控微服务的调用状况，当失败的调用到一定阀值，缺省是5秒内20次调用失败，就会启动熔断机制。熔断机制的注解是@HystrixCommand。
大神论文：https:martinfowler.com/bliki/CircuitBrreaker.html


  @HystrixCommand(fallbackMethod = "circuitBreakerFallBackMethod",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMillisecodds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorthresholdPercentage",value = "60")//失败率达到多少后开启短路
            //翻译上面的配置：当在10秒内，请求次数10次中，失败率达到百分之60后，就开启短路(在HystrixCommandProperties类中有这些参数的默认值，也可以自己配置)
            //失败次数达到这个熔断条件就熔断打开open，慢慢的看能否达到半开half open 状态，然后过度到close状态
    //关于一些其他的配置可以在视频中找到：https://www.bilibili.com/video/BV18E411x7eT?p=61
    })
    
####熔断类型
1）熔断打开：请求不再进行调用当前服务，内部设置的时钟一般为MTTR（平均故障处理时间），当打开时长达到所设定时钟则进入半熔断状态
2）熔断关闭：不会对服务进行熔断
3）熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则认为当前服务恢复正常，关闭熔断。
#### 断路器在什么情况下才开始起作用
涉及到断路器的三个重要参数：快照时间窗，请求总数阀值，错误百分比
1）快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒；
2）请求总阀值：在快照时间窗内，必须满足请求总阀值才有资格进行熔断。默认为20次请求，意味着在10秒内如果hystrix命令的调用次数不够20次，即使左右的请求都失败，短路也不会打开；
3）错误百分比阀值：当请求总数在快照时间窗内超过了阀值，比如发生了30次的调用，如果在这30次中，有5次发生了超时异常，也就是达到了50%的错误率，此时熔断器就会打开。在默认情况下，错误率的设定是50%。

####断路器开启或者关闭的条件
1）当满足一定的阀值的时候，默认10秒内20个请求次数
2）当失败率达到一定的时候默认情况，10秒内超过50%的失败
3）达到以上的阀值 断路器会开启
4）当开启的时候所有的请求都不会转发
5）一段时间后（默认是5秒），这个时候断路器是半开的状态，会让其中的一个请求进行转发。如果成功，断路器会关闭，若失败继续开启。重复4和5的操作。

####断路器打开之后
1）再有请求调用的时候，将不会在调用主逻辑，而是直接调用降级fallback。通过断路器，实现了自动的发现错误并将降级逻辑切换成主逻辑，减少响应掩饰的效果；
2）原来的主逻辑是如何恢复？
  对于这个问题，hystrix也为我们实现了自动恢复功能。当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑称为临时的主逻辑，当休眠时间窗口期，
  断路器将进入半开的壮体啊，释放一个请求到原来的主逻辑上，如果次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然是有问题，断路器继续进入打开状态，休眠时间冲洗计时。
  
### 服务限流
（在阿里巴巴的时候sentinel在深入学习）

##Hystrix的工作的流程
官网；https://github.com/Netflix/Hystrix/wiki/How-it-Works
Hystrix的工作流程

##创建Hystrix Dashboard的监控仪表盘
除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控HystrixDashboad，Hystrix会持续的记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，
包括每秒钟执行多少的请求成功，多少失败等。 Netflix通过hystrix-metrics-event-stream项目实现了以上指标的监控。Springcloud也提供了HystrixDashboad的整合，对监控内容转化成可视化界面。

创建新的模块9001：引入坐标org.springframework.cloud,spring-cloud-starter-netflix-hystrix-dashboad坐标

在需要监控的模块的启动类中需要添加如下配置代码：
比如监控cloud-provider-payment8001：
  /**
     * 此配置是为了服务监控而配置，；与服务容错本身没有关系，Spring 层楼的升级后的坑
     * servletRegistrationBean因为springboot的默认路径不是"/hystrix.stream",
     * 只要在自己的项目中配置上 下面的Servlet就可以了
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<Servlet> servletServletRegistrationBean = new ServletRegistrationBean<>(hystrixMetricsStreamServlet);
        servletServletRegistrationBean.setLoadOnStartup(1);
        servletServletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletServletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return servletServletRegistrationBean;
    }
    
    
















 