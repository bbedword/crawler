package com.lshb.crawler.request.proxy;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.request.Http;

public class ProxyFromWeb implements Runnable {

	private final static Logger log = Logger.getLogger(ProxyFromWeb.class);

	private ProxyIpReg pir = null;
	private boolean isRunning = false;

	public ProxyFromWeb(ProxyIpReg proxy) {
		this.pir = proxy;
	}

	@Override
	public void run() {
		Http http = new Http().timeOut(60 * 1000);
		String u = pir.getUrl();
		int start = pir.getPageStart();
		int end = pir.getPageEnd();
		for (int i = start; i <= end; i++) {
			String url = u.replaceAll(Constant.PROXY_URL_PAGE_CHAR, String.valueOf(i));
			Document doc = null;
			try {
				doc = Jsoup.parse(http.get(url).body());
			} catch (IOException e3) {
				e3.printStackTrace();
			}
			String line_value = pir.getLine();
			Elements trs = doc.select(line_value);
			for (Element e : trs) {
				ProxyIp p = new ProxyIp();
				try {
					p.setIp(e.select(pir.getIp()).get(0).text());
					p.setPort(Integer.parseInt(e.select(pir.getPort()).get(0).text()));
					p.setAnonymity(Boolean.parseBoolean(e.select(pir.getAnonymity()).get(0).text()));
					p.setType(e.select(pir.getType()).get(0).text());
					if (p.isOk(Constant.PROXY_VALIDATE_URL, Constant.PROXY_VALIDATE_CONTENT,
							Constant.PROXY_VALIDATE_CONTENT_CHARSET)) {
						ProxyFromWebManager.addProxy(p);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			// 休息一分钟，让代理网站服务器休息一会
			try {
				Thread.sleep(30 * 1000l);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			if (isRunning) {
				break;
			}
		}
		log.info("完成" + pir.getUrl() + "爬取！代理总数：" + ProxyFromWebManager.proxySize());
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void stop() {
		setRunning(false);
	}
}
