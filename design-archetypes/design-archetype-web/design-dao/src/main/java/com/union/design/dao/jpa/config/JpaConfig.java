package com.union.design.dao.jpa.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(value = {"com.union.design.dao.jpa.repository"}, transactionManagerRef = "jpaTxManager")
//@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.union.design.dao.jpa.entity");
        Map<String, String> jpaPropertyMap = new HashMap<>();
        // 驼峰
        jpaPropertyMap.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                environment.getProperty("spring.jpa.hibernate.naming.physical-strategy",
                        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"
                ));
        factory.setJpaPropertyMap(jpaPropertyMap);
        factory.setDataSource(dataSource);
        return factory;
    }


    @Bean("jpaTxManager")
    public PlatformTransactionManager txManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
        return txManager;
    }

}