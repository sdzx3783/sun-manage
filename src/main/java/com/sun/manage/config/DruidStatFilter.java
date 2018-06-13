package com.sun.manage.config;
import javax.servlet.annotation.WebFilter;  
import javax.servlet.annotation.WebInitParam;

import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.WebStatFilter;  
@Configuration
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",  
    initParams={  
        @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源  
   }  
)  
public class DruidStatFilter extends WebStatFilter {  
  
}  