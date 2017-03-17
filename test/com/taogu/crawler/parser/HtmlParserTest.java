package com.taogu.crawler.parser;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.config.proxy.ProxyConfig;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.parser.HtmlParser;
import com.lshb.crawler.request.Http;
import com.lshb.crawler.util.Config;

public class HtmlParserTest {

	@Test
	public void extraData() {
		HtmlParser hp = new HtmlParser();
		Config.init();
		DataConfig.init();
		ProxyConfig.init();
		String url = "http://care.39.net/a/160706/4890949.html";
		DataReg dataReg = hp.getDataRegFromUrl(url);
		Response res = null;
		String body = null;
		try {
			res = new Http().get(url);
			body = new String(res.bodyAsBytes(), "gb2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = HtmlParser.extraData(dataReg, body);
		System.err.println(json);
	}

}
