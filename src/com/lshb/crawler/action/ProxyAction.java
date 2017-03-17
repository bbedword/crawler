package com.lshb.crawler.action;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lshb.crawler.request.proxy.ProxyFromWebManager;
import com.lshb.crawler.request.proxy.ProxyIp;
import com.lshb.crawler.util.ActionResult;

@At("/proxy")
@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
public class ProxyAction extends BaseAction {

	@At("/list")
	@Ok("jsp:/proxy/list.jsp")
	public Object list() {
		return ProxyFromWebManager.getProxys();
	}

	@At("/listRaw")
	@Ok("raw")
	public Object listRaw() {
		ConcurrentLinkedQueue<ProxyIp> proxys = ProxyFromWebManager.getProxys();
		JSONArray ja = new JSONArray(proxys.size());
		for (ProxyIp p : proxys) {
			ja.add(JSON.toJSON(p));
		}
		return ja;
	}

	@At("/start")
	@Ok("raw")
	public Object start() {
		ProxyFromWebManager.startRun();
		return ActionResult.ok();
	}
}
