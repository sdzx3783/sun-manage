package com.sun.manage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.sun.asssembly.weixin.config.WxConfigProperties;
import com.sun.asssembly.weixin.config.WxpayConfigProperties;

@Configuration
@EnableConfigurationProperties({WxpayConfigProperties.class,WxConfigProperties.class})
@ConditionalOnProperty(prefix = "channel.wxpay",value = "enable",matchIfMissing = true)
public class WxpayAutoConfigure {
	
}
