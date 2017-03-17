package com.lshb.crawler.server;

import org.apache.log4j.Logger;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.config.menu.Menus;
import com.lshb.crawler.config.proxy.ProxyConfig;
import com.lshb.crawler.config.user.UserConfig;

public class SiteSetup implements Setup {

	private final static Logger log = Logger.getLogger(SiteSetup.class);

	@Override
	public void destroy(NutConfig nc) {
		log.info("网站销毁！");
	}

	@Override
	public void init(NutConfig nc) {
		init();
	}

	public static void init() {
		log.info("系统用户配置加载！");
		UserConfig.init();
		log.info("目录配置加载！");
		Menus.init();
		log.info("代理配置加载！");
		ProxyConfig.init();
		log.info("数据配置加载！");
		DataConfig.init();
	}
}
