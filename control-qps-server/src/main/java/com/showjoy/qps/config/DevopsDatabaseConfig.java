package com.showjoy.qps.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = { "com.showjoy.qps.dao" }, sqlSessionFactoryRef = "devOpsSqlSessionFactory")
public class DevopsDatabaseConfig {

    @Resource
    private Environment env;

    @Bean(name = "devOpsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.devops")
    public DataSource initDevOpsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "devOpsTransactionManager")
    public DataSourceTransactionManager transactionManager( DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "devOpsSqlSessionFactory")
    public SqlSessionFactory initDevopsSqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(env.getProperty("mybatis.devops.mapper-locations")));
        return sessionFactory.getObject();
    }
}
