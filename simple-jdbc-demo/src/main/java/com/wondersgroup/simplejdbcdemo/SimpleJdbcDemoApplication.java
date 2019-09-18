package com.wondersgroup.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class SimpleJdbcDemoApplication implements CommandLineRunner {
    @Autowired
    private FooDao fooDao;
    @Autowired
    private BatchFooDao batchFooDao;

    public static void main(String[] args) {
//        SpringApplication.run(SimpleJdbcDemoApplication.class, args);
        /**
         * 对于使用过Spring Boot的开发者来说，程序启动的时候输出的由字符组成的Spring符号并不陌生。这个是Spring Boot为自己设计的Banner：
         *  Banner.Mode.OFF:关闭;
         * * Banner.Mode.CONSOLE:控制台输出，默认方式;
         * * Banner.Mode.LOG:日志输出方式;
         */
        new SpringApplicationBuilder(SimpleJdbcDemoApplication.class).bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE).run(args);
    }

    @Autowired
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate){
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("Foo").usingGeneratedKeyColumns("Id");
    }

    @Autowired
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }



    @Override
    public void run(String... args) throws Exception {
    fooDao.insertData();
    fooDao.listData();
        System.out.println("----------------------------------");
        batchFooDao.batchInsert();
        fooDao.listData();
    }
}
