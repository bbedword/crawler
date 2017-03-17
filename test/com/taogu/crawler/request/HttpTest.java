package com.taogu.crawler.request;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lshb.crawler.request.Http;
import com.lshb.crawler.request.UserAgent;
import com.lshb.crawler.request.proxy.ProxyFromWebManager;
import com.lshb.crawler.request.proxy.ProxyIp;

public class HttpTest {
	private Http http = null;

	@Before
	public void before() {
		ProxyIp p = new ProxyIp();
		p.setIp("183.185.209.99");
		p.setPort(9797);
		ProxyFromWebManager.addProxy(p);
		UserAgent.add(
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2679.0 Safari/537.36");
		http = new Http();
	}

	@Test
	public void get() {
		Response res = null;
		try {
			res = http.getWithProxy("http://www.39.net/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String body = res.body();
		System.err.println(body);
		boolean flag = body.contains("39健康网_中国领先的健康门户网站");
		Assert.assertTrue(flag);
	}

}
