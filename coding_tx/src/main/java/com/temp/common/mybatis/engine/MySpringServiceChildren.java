package com.temp.common.mybatis.engine;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.temp.common.aop.high.MySpringService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class MySpringServiceChildren extends MySpringService {
    @Autowired
    private DataSource dataSource;


    @Bean
    public SqlSessionFactory getSqlSessionFactory(){
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            ClassPathResource[] classPathResources = new ClassPathResource[1];
            Resource resource = new ClassPathResource("com/temp/common/mybatis/tx/dao/mapper/ImUserMapper.xml");
            classPathResources[0]= (ClassPathResource) resource;
            sqlSessionFactoryBean.setMapperLocations(classPathResources);
            return sqlSessionFactoryBean.getObject();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate getSqlSessionTemplate() {
        return new SqlSessionTemplate(getSqlSessionFactory());
    }


}
