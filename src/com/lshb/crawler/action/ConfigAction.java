package com.lshb.crawler.action;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

import com.lshb.crawler.config.data.DataConfig;
import com.lshb.crawler.config.menu.Menus;
import com.lshb.crawler.config.proxy.ProxyConfig;
import com.lshb.crawler.config.user.UserConfig;
import com.lshb.crawler.util.ActionResult;

@At("/init")
@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
public class ConfigAction {

	@At("/menu")
	@Ok("raw")
	public Object menu() {
		Menus.init();
		return ActionResult.ok();
	}

	@At("/proxy")
	@Ok("raw")
	public Object proxy() {
		ProxyConfig.init();
		return ActionResult.ok();
	}

	@At("/data")
	@Ok("raw")
	public Object data() {
		DataConfig.init();
		return ActionResult.ok();
	}

	@At("/user")
	@Ok("raw")
	public Object user() {
		UserConfig.init();
		return ActionResult.ok();
	}
}
