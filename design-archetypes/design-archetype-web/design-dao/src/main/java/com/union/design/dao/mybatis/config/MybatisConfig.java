package com.union.design.dao.mybatis.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 使用MapperScan批量扫描所有的Mapper接口
 *
 * @author liaox
 * @date 2021/4/22
 */
@Configuration
@MapperScan("com.union.design.dao.mybatis.mapper")
//@EnableTransactionManagement
public class MybatisConfig  {

    @Autowired
    private DataSource dataSource;

//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.master")
//    public DataSource masterDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.slave")
//    public DataSource slaveDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean(value = {"mybatisTxManager"})
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}