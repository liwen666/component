package com.temp.common.mybatis.engine;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.temp.common.mybatis.tx.dao.ImUserMapper;
import com.temp.common.mybatis.tx.domain.ImUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

@MapperScan("com.temp.common.mybatis")
@ComponentScan(basePackages = "com.temp.common.mybatis")
public class MybatisMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.register(MybatisMain.class);
        appContext.refresh();
        MySpringServiceChildren bean = appContext.getBean(MySpringServiceChildren.class);
        MybatisMain bean1 = appContext.getBean(MybatisMain.class);
        System.out.println(bean);
        System.out.println(bean1);
        ImUserMapper baseMapper = appContext.getBean(ImUserMapper.class);
        System.out.println(baseMapper);

        ImUser user = baseMapper.selectMy();
        System.out.println(user);


        QueryWrapper<ImUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", "428edf4fcf0242a49198d32845b0b1ec");
        List<ImUser> messageList = baseMapper.selectList(queryWrapper);
        for (ImUser u : messageList) {
            System.out.println(u.getName());
        }

    }

    @Bean
    public DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://192.168.42.136:3306/vim?useUnicode=true&useSSL=false&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
//        基本属性 url、user、password
//                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
//        <property name="url" value="jdbc:mysql://192.168.9.180:3306/hqbpmn?useUnicode=true"/>
//        <property name="username" value="root"/>
//        <property name="password" value="root"/>
//
//                配置初始化大小、最小、最大
//                <property name="initialSize" value="10"/>
//        <property name="minIdle" value="10"/>
//        <property name="maxActive" value="50"/>
//                配置获取连接等待超时的时间
//                <property name="maxWait" value="60000"/>
//                配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//                <property name="timeBetweenEvictionRunsMillis" value="60000"/>
//
//                配置一个连接在池中最小生存的时间，单位是毫秒
//                <property name="minEvictableIdleTimeMillis" value="300000"/>
//
//        <property name="validationQuery" value="SELECT 'x'"/>
//        <property name="testWhileIdle" value="true"/>
//        <property name="testOnBorrow" value="false"/>
//        <property name="testOnReturn" value="false"/>
//
//                打开PSCache，并且指定每个连接上PSCache的大小  如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。
//        <property name="poolPreparedStatements" value="false"/>
//        <property name="maxPoolPreparedStatementPerConnectionSize" value="20"/>
//
//                配置监控统计拦截的filters
//                <property name="filters" value="wall,stat"/>
        return druidDataSource;
    }

}
