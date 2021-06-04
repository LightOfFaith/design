package com.union.design.dao.mybatis.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 使用MapperScan批量扫描所有的Mapper接口
 *
 * @author liaox
 * @date 2021/4/22
 */
@Configuration
@MapperScan("com.union.design.dao.mybatis.mapper")
//@EnableTransactionManagement
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resourcePatternResolver.getResources("classpath*:mappers/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        return sqlSessionFactoryBean;
    }

    @Bean(value = { "mybatisPlusTxManager"})
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}