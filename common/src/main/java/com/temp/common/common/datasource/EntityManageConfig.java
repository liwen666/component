//package com.temp.common.common.datasource;
//
//
//import com.temp.common.common.datasource.config.DataSourceAutoConfig;
//import com.temp.common.common.datasource.config.HandlerDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
//import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.Properties;
//
//
//@Configuration
//@AutoConfigureAfter(DataSourceAutoConfig.class)
//public class EntityManageConfig {
//
//
//
//    @Bean(value = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean manEntityManagerFactory(
//            @Autowired @Qualifier("routeDateSource") HandlerDataSource handlerDataSource
//    ) {
//        LocalContainerEntityManagerFactoryBean factory =
//                new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(handlerDataSource);
//        factory.setPackagesToScan("jrx.anyest.**");
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.enable_lazy_load_no_trans",true);
//        jpaProperties.put("hibernate.ddl-auto","update");
//        jpaProperties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
//        jpaProperties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
//        jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//        factory.setJpaProperties(jpaProperties);
//        return factory;
//    }
//
//    @Bean(value = "transactionManager")
//    public PlatformTransactionManager manTransactionManager(
//            @Autowired LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        EntityManagerFactory factory = entityManagerFactory.getObject();
//        return new JpaTransactionManager(factory);
//
//    }
//
//
//
//}
