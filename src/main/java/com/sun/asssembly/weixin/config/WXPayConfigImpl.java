package com.sun.asssembly.weixin.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.sun.asssembly.weixin.core.IWXPayDomain;
import com.sun.asssembly.weixin.core.WXPayConfig;
import com.sun.asssembly.weixin.core.WXPayConstants.SignType;
import com.sun.asssembly.weixin.core.WXPayDomainSimpleImpl;


public class WXPayConfigImpl extends WXPayConfig{
	
	Logger logger = LoggerFactory.getLogger(WXPayConfigImpl.class);
	
	private PayConfig payConfig;
	
	private WxpayConfigProperties wxpayConfigProperties;

    private byte[] certData;
    
    public WXPayConfigImpl(WxpayConfigProperties wxpayConfigProperties,PayConfig payConfig){
    	this.wxpayConfigProperties = wxpayConfigProperties;
    	this.payConfig = payConfig;
        String secretCert = payConfig.getSecretCert();
        init(secretCert);
    }
    
    protected void init(String secretCert){
    	if(!StringUtils.isEmpty(secretCert)){
    		certData = Base64.decodeBase64(secretCert);
    	}
    }

    public String getAppID() {
        return payConfig.getAppId();
    }

    public String getMchID() {
        return payConfig.getMchId();
    }

    public String getKey() {
        return payConfig.getAppSecret();
    }
    
    @Override
	public SignType getSignType() {
    	if(payConfig.getSignType().equals(SignType.MD5.name())){
    		return SignType.MD5;
    	}
    	return SignType.HMACSHA256;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return wxpayConfigProperties.getConnectTimeout();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return wxpayConfigProperties.getReadTimeout();
    }

    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return wxpayConfigProperties.getReportWorkerNum();
    }

    @Override
    public int getReportBatchSize() {
        return wxpayConfigProperties.getReportBatchSize();
    }
    
    @Override
    public boolean isSandBox() {
    	return payConfig.isSandbox();
    }
}
