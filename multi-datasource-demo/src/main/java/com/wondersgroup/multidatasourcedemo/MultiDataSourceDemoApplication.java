package com.wondersgroup.multidatasourcedemo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Reference;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *  排除spring管理的数据源，事务和JdbcTemplate自动注入
 */
@SpringBootApplication(exclude ={ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@Slf4j
public class MultiDataSourceDemoApplication  {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MultiDataSourceDemoApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE).run(args);
    }
    // 配置foo数据源
    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties fooDataSourcePropreties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDataSource(){
        DataSourceProperties dataSourceProperties = fooDataSourcePropreties();
        log.info("foo datasource{}",dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    @Autowired
    public PlatformTransactionManager footxManager(DataSource fooDataSource){
            return new  DataSourceTransactionManager(fooDataSource);
    }

    // 配置bar数据源
    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourcePropreties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDataSource(){
        DataSourceProperties dataSourceProperties = barDataSourcePropreties();
        log.info("bar datasource{}",dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    @Autowired
    public PlatformTransactionManager bartxManager(DataSource barDataSource){
        return new  DataSourceTransactionManager(barDataSource);
    }

}
