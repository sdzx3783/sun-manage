package com.sun.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.zookeeper.curator.CuratorClient;

/**  
 * @Title:  Zookeeper.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月16日 下午3:11:16   
 */
@Configuration
public class ZookeeperConfigurer {
	
//	@Bean(initMethod = "connectServer", destroyMethod = "disConnectServer")
    public CuratorClient curatorClient(){
		return new CuratorClient();
	}
}
