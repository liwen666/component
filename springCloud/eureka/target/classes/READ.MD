#访问地址
     http://localhost:8761/
     用户名 user  密码 12346
     
#<!-- 用于注册中心访问账号认证 -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-security</artifactId>
            </dependency> 
     这个依赖不引入，只需要ip端口就能将微服务注册到eureka
     
     
     
# 添加配置文件application.properties，并且添加如下参数，才能创建一个真正可以使用的服务注册中心。
     
     ######eureka服务端######
     
     spring.application.name=eureka-server
     
      #驱逐下线的服务，间隔,5秒，默认是60，建议开发和测试环境配置
     
      eureka.server.evictionIntervalTimerInMs=10000
     
     server.port=8761
     
     #是否需要注册到注册中心，因为该项目本身作为服务注册中心，所以为false
     
      eureka.client.register-with-eureka=false
     
      #是否需要从注册中心获取服务列表，原因同上，为false
     
      eureka.client.fetch-registry=false
     
      security.basic.enabled=true
     
     security.user.name=admin
     
     security.user.password=123
     
     #注册服务器的地址：服务提供者和服务消费者都要依赖这个地址
     
      eureka.client.serviceUrl.defaultZone=http://admin:123@localhost:8761/eureka
     
      ###Eureka自我保护机制,为true表示开，false表示关，默认为开####
     
      eureka.server.enable-self-preservation=true
     
      
     
     启动注册服务，并访问：http://localhost:8761，就可以看到如下界面。