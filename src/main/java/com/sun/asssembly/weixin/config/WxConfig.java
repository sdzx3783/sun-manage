package com.sun.asssembly.weixin.config;

import java.io.InputStream;

public class WxConfig {
		
	public static final String DOMAIN = "api.weixin.qq.com";

	public static final String TOKEN_URI = "/cgi-bin/token";
	
	public static final String JS_API_TICKET_URI = "/cgi-bin/ticket/getticket";

	/**
	 * 微信公众号获取用户信息uri
	 */
	public static final String USER_INFO_URI = "/cgi-bin/user/info";
	/**
	 * 微信根据code获取openIdUri
	 */
	public static final String OAUTH2_TOKEN_URI = "/sns/oauth2/access_token";
	/**
	 * 微信公众号菜单创建uri
	 */
	public static final String MENU_CREATE_URI = "/cgi-bin/menu/create?access_token=";
	/**
	 * 微信网页授权uri
	 */
	public static final String GET_OAUTH2_URI = "/connect/oauth2/authorize";

	/**
	 * 微信公众号模板消息推送uri
	 */
	public static final String SEND_URI = "/cgi-bin/message/template/send";

	/**
	 * 微信小程序模板消息推送uri
	 */
	public static final String SEND_WXOPEN_URI = "/cgi-bin/message/wxopen/template/send";
	
	/**
	 * 微信小程序获取场景二维码
	 */
	public static final String WP_SCENE_QOCODE = "/wxa/getwxacodeunlimit?access_token=";
	
	/**
	 * 微信小程序获取二维码
	 */
	public static final String WP_QOCODE = "/wxa/getwxacode?access_token=";
	
	
	public static final String WEB_LOGIN_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
	public static final String WEB_LOGIN_CALLBACK_URL="http://127.0.0.1/callback";
	int readTimeoutMs = 1000;
	
	int connectTimeoutMs = 1000;
	
	String mchID;
	
	boolean useCert = false;
	
	String domain;
	
	String uri;

	public int getReadTimeoutMs() {
		return readTimeoutMs;
	}

	public void setReadTimeoutMs(int readTimeoutMs) {
		this.readTimeoutMs = readTimeoutMs;
	}

	public int getConnectTimeoutMs() {
		return connectTimeoutMs;
	}

	public void setConnectTimeoutMs(int connectTimeoutMs) {
		this.connectTimeoutMs = connectTimeoutMs;
	}

	public String getMchID() {
		return mchID;
	}

	public void setMchID(String mchID) {
		this.mchID = mchID;
	}

	public boolean isUseCert() {
		return useCert;
	}

	public void setUseCert(boolean useCert) {
		this.useCert = useCert;
	}
	
	public InputStream getCertStream(){
		return null;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	
}
