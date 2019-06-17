package com.am.rbacsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = {"com.am.rbacsystem.dao"})
@EnableTransactionManagement
//扫描过滤器上的注解
@ServletComponentScan
public class RbacsystemApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RbacsystemApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RbacsystemApplication.class);
    }
}
