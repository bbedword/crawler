package com.lshb.crawler.request.proxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Connection.Response;

import com.lshb.crawler.request.Http;

public class ProxyIp {

	private String ip;
	private int port;
	// 代理地址
	private String address;
	// 是否高匿
	private boolean isAnonymity;
	// 类型
	private String type;

	private ProxyCallInfo callInfo = new ProxyCallInfo();

	//判断代理是否已经超过失败次数
	public boolean isSealed(String host) {
		return callInfo.isSealed(host);
	}

	//增加代理在host下的失败次数
	public int addFailHost(String host) {
		return callInfo.addFailHost(host);
	}

	public AtomicInteger rmHost(String host) {
		return callInfo.rmHost(host);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAnonymity() {
		return isAnonymity;
	}

	public void setAnonymity(boolean isAnonymity) {
		this.isAnonymity = isAnonymity;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isOk(String url, String content, String charset) {
		Response res = null;
		try {
			res = new Http().proxy(this).getWithProxy(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (res == null) {
			return false;
		}
		String body = null;
		try {
			body = new String(res.bodyAsBytes(), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return body.contains(content);
	}

	@Override
	public String toString() {
		return ip + ":" + port;
	}

	public ProxyCallInfo getErrorInfo() {
		return callInfo;
	}

	public void setErrorInfo(ProxyCallInfo callInfo) {
		this.callInfo = callInfo;
	}

	public boolean isNeedChange() {
		return callInfo.needChange();
	}
}
