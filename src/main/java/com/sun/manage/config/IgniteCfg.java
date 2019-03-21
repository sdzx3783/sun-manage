package com.sun.manage.config;

import com.sun.manage.repository.ignite.Person;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:IgniteCfg配置
 * @Author: sunzhao
 * @Create on: 2019-01-16 13:50
 */
@Configuration
public class IgniteCfg{
    /**
     * Creating Apache Ignite Instance bean.
     * @return Ignite
     */
    @Bean
    public Ignite igniteInstance(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("springDataNode");

        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(false);

        // Defining and creating a new cache to be used by Ignite Spring Data repository.
        CacheConfiguration ccfg = new CacheConfiguration("PersonCache");

        // Setting SQL schema for the cache.
        ccfg.setIndexedTypes(Long.class, Person.class);

        cfg.setCacheConfiguration(ccfg);
        cfg.setClientMode(false);
        return Ignition.start(cfg);
    }
}
