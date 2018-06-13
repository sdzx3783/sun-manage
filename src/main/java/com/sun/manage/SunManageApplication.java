package com.sun.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ComponentScan(basePackages= {"com.sun"})
@ServletComponentScan(basePackages={"com.sun"})
@ImportResource(locations = {"classpath:app-action.xml"})
public class SunManageApplication{

    public static void main(String[] args) {
        SpringApplication.run(SunManageApplication.class, args);
    }
   
}