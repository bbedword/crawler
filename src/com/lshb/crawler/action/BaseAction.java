package com.lshb.crawler.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.nutz.mvc.Mvcs;

/**
 * @author speedy
 */
public abstract class BaseAction {

	public void setReqAttr(String key, Object value) {
		Mvcs.getReq().setAttribute(key, value);
	}

	public Object getReqAttr(String key) {
		return Mvcs.getReq().getAttribute(key);
	}

	public void setSessionAttr(String key, Object value) {
		Mvcs.getHttpSession().setAttribute(key, value);
	}

	public void rmSessionAttr(String key) {
		Mvcs.getHttpSession().removeAttribute(key);
	}

	public Object getSessionAttr(String key) {
		return Mvcs.getHttpSession().getAttribute(key);
	}

	public String getUser() {
		return (String) getSessionAttr("user");
	}

	public void setUser(String user) {
		setSessionAttr("user", user);
	}

	public void rmUser() {
		rmSessionAttr("user");
	}

	public String encodeUrl(String url) {
		String u = null;
		try {
			u = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return u;
	}
}
