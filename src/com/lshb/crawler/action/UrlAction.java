package com.lshb.crawler.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.manager.CrawlerManager;
import com.lshb.crawler.util.StringUtil;

@At("/url")
@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
public class UrlAction extends BaseAction {

	private final static Logger log = Logger.getLogger(UrlAction.class);

	@At("/list")
	@Ok("jsp:/url/list.jsp")
	public void list(@Param("name") String name) {
		// --TODO url队列中的url数量很多，这里是否要全部显示，此方法暂时没有用
		List<String> urls = CrawlerManager.getCrawlerGroup(name).getUrlQueue().getUrls();
		setReqAttr("urls", urls);
	}

	@At("/listRaw")
	@Ok("raw")
	public Object listRaw(@Param("name") String name) {
		List<String> urls = CrawlerManager.getCrawlerGroup(name).getUrlQueue().getUrls();
		return urls;
	}

	@At("/remove")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object remove(@Param("name") String name, @Param("url") String url) {
		CrawlerManager.getCrawlerGroup(name).getUrlQueue().remove(url);
		return encodeUrl(name);
	}

	@At("/add")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object add(@Param("name") String name) {
		File file = new File(Constant.URL_USERDEFINE_DIR + File.separator + name + File.separator + name);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
					continue;
				}
				if (line.startsWith("http")) {
					CrawlerManager.getCrawlerGroup(name).addUrl(line.trim());
				} else {
					log.warn("url错误:" + line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeUrl(name);
	}

	@At("/addOne")
	@Ok("redirect:/crawler/view?name=${obj}")
	public Object addOne(@Param("name") String name, @Param("url") String url) {
		CrawlerManager.getCrawlerGroup(name).addUrl(url);
		return encodeUrl(name);
	}

	@At("/addNotFilter")
	@Ok("redirect:list")
	public Object addNotFilter(@Param("name") String name, @Param("url") String url) {
		CrawlerManager.getCrawlerGroup(name).getUrlQueue().addNotFilter(url);
		return encodeUrl(name);
	}
}
