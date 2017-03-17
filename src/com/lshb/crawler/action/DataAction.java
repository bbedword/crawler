package com.lshb.crawler.action;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.crawler.CrawlerGroup;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.data.FilterType;
import com.lshb.crawler.data.UrlFilter;
import com.lshb.crawler.manager.CrawlerManager;
import com.lshb.crawler.parser.HtmlParser;
import com.lshb.crawler.parser.ValueType;
import com.lshb.crawler.request.Http;

@At("/data")
@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
public class DataAction extends BaseAction {

	private final static Logger log = Logger.getLogger(DataAction.class);

	@At("/showAdd")
	@Ok("jsp:/data/add.jsp")
	public void showAdd(@Param("name") String name) {
		setReqAttr("name", name);
		setReqAttr("FilterTypes", FilterType.values());
		setReqAttr("valueTypes", ValueType.values());
	}

	@At("/add")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object add(@Param("name") String name, @Param("::data.") DataReg data) {
		CrawlerManager.getCrawlerGroup(name).addDataReg(data);
		String n = DataConfig.toFile(name, data);
		log.info(getUser() + "添加数据模板文件：" + n + ",urlReg:" + data.getUrlReg());
		return encodeUrl(name);
	}

	@At("/addFilter")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object add(@Param("name") String name, @Param("::filter") List<UrlFilter> filters) {
		CrawlerGroup cg = CrawlerManager.getCrawlerGroup(name);
		cg.getParser().getFilters().clear();
		for (UrlFilter filter : filters) {
			cg.addUrlFilterReg(filter);
		}
		String n = DataConfig.toFile(name, filters);
		log.info(getUser() + "修改url过滤模板文件：" + n);
		return encodeUrl(name);
	}

	@At("/view")
	@Ok("jsp:/data/view.jsp")
	public void view(@Param("name") String name, @Param("urlReg") String urlReg) {
		setReqAttr("name", name);
		setReqAttr("valueTypes", ValueType.values());
		setReqAttr("FilterTypes", FilterType.values());
		setReqAttr("data", CrawlerManager.getCrawlerGroup(name).getDataReg(urlReg));
	}

	@At("/viewFilter")
	@Ok("jsp:/data/filterView.jsp")
	public void viewFilter(@Param("name") String name) {
		setReqAttr("name", name);
		setReqAttr("FilterTypes", FilterType.values());
		Collection<UrlFilter> filters = CrawlerManager.getCrawlerGroup(name).getParser().getFilters().values();
		setReqAttr("filters", filters);
		setReqAttr("size", filters.size());
	}

	@At("/remove")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object remove(@Param("name") String name, @Param("urlReg") String urlReg) {
		CrawlerManager.getCrawlerGroup(name).rmDataReg(urlReg);
		String fileName = DataConfig.rmFile(urlReg);
		log.info(getUser() + "删除数据模板文件：" + fileName + ",urlReg:" + urlReg);
		return encodeUrl(name);
	}

	@At("/test")
	@Ok("raw")
	public Object test(@Param("name") String name, @Param("urlReg") String urlReg, @Param("url") String url) {
		CrawlerGroup cg = CrawlerManager.getCrawlerGroup(name);
		DataReg dataReg = cg.getDataReg(urlReg);
		String charset = cg.getCharset();
		Http http = new Http();
		http.userAgent(
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2679.0 Safari/537.36");
		String html = null;
		try {
			html = new String(http.get(url).bodyAsBytes(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject data = HtmlParser.extraData(dataReg, html);
		return data;
	}

}
