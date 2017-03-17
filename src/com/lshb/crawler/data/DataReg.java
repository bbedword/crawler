package com.lshb.crawler.data;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.parser.ValueReg;
import com.lshb.crawler.util.StringUtil;

public class DataReg {

	private String urlReg;
	private String isOk;
	private Map<String, ValueReg> params = new HashMap<>();
	private String db;
	private String dataTable;
	private boolean isStoreHtml;
	private String htmlTable;

	public String getUrlReg() {
		return urlReg;
	}

	public void setUrlReg(String urlReg) {
		this.urlReg = urlReg;
	}

	public Map<String, ValueReg> getParams() {
		return params;
	}

	public void setParams(Map<String, ValueReg> params) {
		this.params = params;
	}

	public static void main(String[] args) {
		DataReg dr = new DataReg();
		dr.setUrlReg("http://care.39.net/a/[^/]*/[^\\.]*.html");
		Map<String, ValueReg> map = new HashMap<>();
		map.put("发布时间",
				new ValueReg("#art_box > div.art_left > div.art_box > div.art_info > div.date > em:nth-child(1)"));
		map.put("目录", new ValueReg("#art_topbar > div > span.art_location"));
		map.put("来源",
				new ValueReg("#art_box > div.art_left > div.art_box > div.art_info > div.date > em:nth-child(2) > a"));
		map.put("核心提示", new ValueReg("#art_box > div.art_left > div.art_box > p"));
		map.put("内容", new ValueReg("#contentText"));
		dr.setParams(map);
		dr.setDb("jiangkang");
		dr.setDataTable("zixun");
		dr.setStoreHtml(true);
		dr.setHtmlTable("html");
		DataConfig.toFile("test", dr);
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getHtmlTable() {
		return htmlTable;
	}

	public void setHtmlTable(String htmlTable) {
		this.htmlTable = htmlTable;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public boolean getIsStoreHtml() {
		return isStoreHtml;
	}

	public void setStoreHtml(boolean isStoreHtml) {
		this.isStoreHtml = isStoreHtml;
	}

	public String getIsOk() {
		return isOk;
	}

	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}

	// 通过isOk字段验证网页是否要的数据网页
	public boolean isOk(String html) {
		if (StringUtil.isBlank(isOk)) {
			return true;
		}
		Elements es = Jsoup.parse(html).select(isOk);
		if (es != null && !es.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "DataReg [urlReg=" + urlReg + ", isOk=" + isOk + ", params=" + params + ", db=" + db + ", dataTable="
				+ dataTable + ", isStoreHtml=" + isStoreHtml + ", htmlTable=" + htmlTable + "]";
	}
}
