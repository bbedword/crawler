package com.lshb.crawler.action;

import org.apache.log4j.Logger;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.crawler.CrawlerConfig;
import com.lshb.crawler.crawler.CrawlerGroup;
import com.lshb.crawler.manager.CrawlerManager;

@At("/crawler")
@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
public class CrawlerAction extends BaseAction {

	private final static Logger log = Logger.getLogger(CrawlerAction.class);

	@At("/list")
	@Ok("jsp:/crawler/list.jsp")
	public void list() {
		setReqAttr("crawlers", CrawlerManager.getMaps().values());
	}

	@At("/add")
	@Ok("redirect:list")
	public void add(@Param("::config") CrawlerConfig cc) {
		CrawlerManager.createCrawlerGroup(cc);
		DataConfig.toFile(cc);
		log.info(getUser() + "添加了一个爬虫任务:" + cc.getName());
	}

	@At("/addThread")
	@Ok("redirect:view?name=${obj}")
	public Object addThread(@Param("name") String name, @Param("num") int num) {
		if (num >= 0) {
			CrawlerManager.getCrawlerGroup(name).addCrawler(num);
		} else {
			CrawlerManager.getCrawlerGroup(name).subCrawler(-num);
		}
		return encodeUrl(name);
	}

	@At("/view")
	@Ok("jsp:/crawler/view.jsp")
	public void view(@Param("name") String name) {
		CrawlerGroup cg = CrawlerManager.getCrawlerGroup(name);
		setReqAttr("cg", cg);
		// url队列
		long urlAllNum = cg.getUrlQueue().getUrlNum();
		setReqAttr("urlAllNum", urlAllNum);
		int urlNotFinish = cg.getUrlQueue().getUrls().size();
		setReqAttr("urlNotFinish", urlNotFinish);
	}

	@At("/remove")
	@Ok("redirect:list")
	public void remove(@Param("name") String name) {
		CrawlerManager.removeCrawlerGroup(name);
	}

	@At("/start")
	@Ok("redirect:list")
	public void start(@Param("name")final String name) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				CrawlerManager.start(name);
			}
		}).start();
	}

	@At("/stop")
	@Ok("redirect:list")
	public void stop(@Param("name") String name) {
		CrawlerManager.stop(name);
	}
}
