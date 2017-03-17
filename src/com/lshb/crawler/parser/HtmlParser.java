package com.lshb.crawler.parser;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.Constant;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.data.UrlFilter;

public class HtmlParser {

	private ConcurrentHashMap<String, DataReg> dataRegs = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, UrlFilter> filters = new ConcurrentHashMap<>();

	public List<String> extraUrl(String html) {
		Document doc = Jsoup.parse(html);
		Elements es = doc.getElementsByTag("a");
		List<String> list = new ArrayList<>();
		for (Element e : es) {
			String url = e.absUrl("href");
			if (isUrlQueueNeeded(url)) {
				list.add(url);
			}
		}
		return list;
	}

	// 判断url是否需要放入url_queue队列中
	public boolean isUrlQueueNeeded(String url) {
		if (StringUtil.isBlank(url)) {
			return false;
		}
		for (UrlFilter uf : filters.values()) {
			try {
				if (uf.filter(url))
					return true;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public static JSONObject extraData(DataReg reg, String html) {
		JSONObject json = new JSONObject();
		Document doc = Jsoup.parse(html);
		Map<String, ValueReg> params = reg.getParams();
		for (String key : params.keySet()) {
			try {
				String value = params.get(key).getValue(doc);
				json.put(key, value.trim());
			} catch (Exception e) {
				json.put(key, Constant.DATA_PARSER_ERROR_STR);
			}
		}
		return json;
	}

	public ConcurrentHashMap<String, DataReg> getDataRegs() {
		return dataRegs;
	}

	public void setDataRegs(ConcurrentHashMap<String, DataReg> dataRegs) {
		this.dataRegs = dataRegs;
	}

	public DataReg getDataRegFromUrl(String url) {
		for (String urlReg : dataRegs.keySet()) {
			if (url.matches(urlReg)) {
				return dataRegs.get(urlReg);
			}
		}
		return null;
	}

	public DataReg getDataReg(String urlReg) {
		return dataRegs.get(urlReg);
	}

	public void addDataReg(DataReg data) {
		if (validateDataReg(data)) {
			dataRegs.put(data.getUrlReg(), data);
		}
	}

	public DataReg rmDataReg(String urlReg) {
		return dataRegs.remove(urlReg);
	}

	public void addFilterReg(UrlFilter uf) {
		if (validateUrlFilterReg(uf)) {
			filters.put(uf.getFilter(), uf);
		}
	}

	public UrlFilter rmFilterReg(String filterReg) {
		return filters.remove(filterReg);
	}

	private boolean validateUrlFilterReg(UrlFilter uf) {
		// TODO 验证过滤配置是否正常

		return true;
	}

	public boolean validateDataReg(DataReg data) {
		// TODO 验证数据配置是否正常

		return true;
	}

	public ConcurrentHashMap<String, UrlFilter> getFilters() {
		return filters;
	}

	public void setFilters(ConcurrentHashMap<String, UrlFilter> filters) {
		this.filters = filters;
	}
}