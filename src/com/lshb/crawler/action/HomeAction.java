package com.lshb.crawler.action;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
public class HomeAction extends BaseAction {

	@At("/home")
	@Ok("jsp:${obj}")
	public Object showHomePage() {
		String user = getUser();
		if (user == null) {
			return "/login.jsp";
		}
		return "/home.jsp";
	}
	
	

}
