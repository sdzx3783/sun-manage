package com.sun.asssembly.weixin.core;

/**
 * 微信支付异常
 * @author apple
 *
 */
public class WxpayException extends RuntimeException {

	private static final long serialVersionUID = -752789182948849678L;

	protected String retCode;
	
	protected String message;
	
	public WxpayException(String retCode,String message){
		this.retCode = retCode;
		this.message = message;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
