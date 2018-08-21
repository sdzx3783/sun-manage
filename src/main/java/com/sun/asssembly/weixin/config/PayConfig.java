package com.sun.asssembly.weixin.config;

/**
 * 支付配置
 * 
 * @author admin
 *
 */
public class PayConfig {

	/**
	 * 支付渠道类型 （2:储值卡 3:微支付公众号 4:微信扫码支付 5:支付宝扫码 6:支付宝APP 10:微信条码 11:支付宝条码
	 * 12:微信APP支付 13:易爱APP余额支付）
	 */
	private Integer payType;

	/**
	 * 支付时显示主体
	 */
	private String body;

	// private String payUrl;

	// private String certPath;

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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

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
		builder.append("PayConfig [payType=");
		builder.append(payType);
		builder.append(", body=");
		builder.append(body);
		builder.append(", appId=");
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