package com.cqupt.prizetool;

import com.cqupt.prizetool.config.datasource.DruidDataSourceBuilder;
import com.cqupt.prizetool.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
@EnableAsync
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.cqupt.prizetool.mapper.master")
@MapperScan("com.cqupt.prizetool.mapper.slave")
public class PrizeToolApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {

        SpringApplication.run(PrizeToolApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){

        return application.sources(PrizeToolApplication.class);
    }

    Thread thread = new Thread();
}
