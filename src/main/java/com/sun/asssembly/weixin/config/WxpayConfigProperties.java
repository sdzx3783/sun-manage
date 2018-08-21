package com.sun.asssembly.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "channel.wxpay")
public class WxpayConfigProperties {

	/**
	 * 通知回调
	 */
	private String notifyUrl;
	
	/**
	 * HTTP(S) 连接超时时间，单位毫秒
	 */
	private int connectTimeout = 6*1000;
	
	/**
	 * HTTP(S) 读数据超时时间，单位毫秒
	 */
	private int readTimeout = 8*1000;
	
	/**
	 *  获取WXPayDomain, 用于多域名容灾自动切换
	 */
	private String payDomain;
	
	/**
	 * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
	 */
	private boolean autoReport = true;
	
	/**
	 * 进行健康上报的线程的数量
	 */
	private int reportWorkerNum = 5;
	
	/**
	 * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受 
	 */
	private int reportQueueMaxSize = 10000;
	
	/**
	 * 批量上报，一次最多上报多个数据
	 */
	private int reportBatchSize = 5;

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getPayDomain() {
		return payDomain;
	}

	public void setPayDomain(String payDomain) {
		this.payDomain = payDomain;
	}

	public boolean isAutoReport() {
		return autoReport;
	}

	public void setAutoReport(boolean autoReport) {
		this.autoReport = autoReport;
	}

	public int getReportWorkerNum() {
		return reportWorkerNum;
	}

	public void setReportWorkerNum(int reportWorkerNum) {
		this.reportWorkerNum = reportWorkerNum;
	}

	public int getReportQueueMaxSize() {
		return reportQueueMaxSize;
	}

	public void setReportQueueMaxSize(int reportQueueMaxSize) {
		this.reportQueueMaxSize = reportQueueMaxSize;
	}

	public int getReportBatchSize() {
		return reportBatchSize;
	}

	public void setReportBatchSize(int reportBatchSize) {
		this.reportBatchSize = reportBatchSize;
	}

}
