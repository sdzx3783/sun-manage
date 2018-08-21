package com.sun.asssembly.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: WxConfigProperties.java
 * @Description: TODO
 * @author: sunzhao
 * @date: 2018年8月9日 下午3:23:26
 */
@ConfigurationProperties(prefix = "wx.config")
public class WxConfigProperties {
	/**
	 * 应用ID
	 */
	private String appId;

	/**
	 * 密钥（md5加密时配置）
	 */
	private String appSecret;

	/**
	 * 渠道商户ID（合作者ID）
	 */
	private String mchId;

	/**
	 * 应用Key
	 */
	private String appKey;

	/**
	 * 签名方式
	 */
	private String signType;

	/**
	 * 退款证书
	 */
	private String secretCert;

	/**
	 * 是否沙箱环境
	 */
	private boolean sandbox;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSecretCert() {
		return secretCert;
	}

	public void setSecretCert(String secretCert) {
		this.secretCert = secretCert;
	}

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WxConfig [appId=");
		builder.append(appId);
		builder.append(", appSecret=");
		builder.append(appSecret);
		builder.append(", mchId=");
		builder.append(mchId);
		builder.append(", appKey=");
		builder.append(appKey);
		builder.append(", signType=");
		builder.append(signType);
		builder.append(", secretCert=");
		builder.append(secretCert);
		builder.append("]");
		return builder.toString();
	}

}
