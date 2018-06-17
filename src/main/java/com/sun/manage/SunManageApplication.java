package com.sun.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages= {"com.sun"})
@ServletComponentScan(basePackages={"com.sun"})
@EnableJpaRepositories
@EnableTransactionManagement
@ImportResource(locations = {"classpath:app-action.xml"})
@SpringBootApplication
public class SunManageApplication{

    public static void main(String[] args) {
        SpringApplication.run(SunManageApplication.class, args);
    }
   
}