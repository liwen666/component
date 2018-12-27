# jar依赖下面不能加上下面标签
          <scope>test</scope>
          否则依赖引入不了
          
#依赖引入 以下两个即可
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>
        <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
        </dependency>
        
#logback  功能
         自定义日志路径，，通过系统  user.dir
         正则匹配日志输出对象的输出方式
         