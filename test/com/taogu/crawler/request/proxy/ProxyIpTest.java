/**
 * 
 */
package com.taogu.crawler.request.proxy;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.lshb.crawler.config.proxy.ProxyConfig;
import com.lshb.crawler.request.proxy.ProxyIp;

/**
 * @author speedy
 *
 */
public class ProxyIpTest {

	@Before
	public void before() {
		ProxyConfig.init();
	}

	@Test
	public void test() {
		String ip = "180.102.34.241";
		int port = 8998;
		ProxyIp p = new ProxyIp();
		p.setIp(ip);
		p.setPort(port);
		String url = "http://fk.39.net/";
		String content = "39健康";
		System.err.println(p.toString());
		boolean ok = p.isOk(url, content, "gb2312");
		assertTrue(ok);
	}

}
