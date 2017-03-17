package com.lshb.crawler.action;

import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.lshb.crawler.config.menu.Menus;
import com.lshb.crawler.config.user.UserConfig;

@IocBean
@At("/user")
public class UserAction extends BaseAction {
	private final static Logger log = Logger.getLogger(UserAction.class);

	@At("/logout")
	@Ok("redirect:${obj}")
	public String logout() {
		log.info(getUser() + "退出系统！");
		rmUser();
		return "/home";
	}

	@At("/menu")
	@Ok("raw")
	@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
	public Object menu() {
		return Menus.getMs();
	}

	@At("/changePassword")
	@Ok("redirect:${obj}")
	@Filters(@By(type = CheckSession.class, args = { "user", "/login.jsp" }))
	public Object changePassword(@Param("oldPassword") String oldPassword, @Param("password") String password,
			@Param("rePassword") String rePassword) {
		String user = getUser();
		if (UserConfig.has(user, oldPassword)) {
			if (password.equals(rePassword)) {
				UserConfig.put(user, password);
				UserConfig.writeUser(user, password);
				log.info(user + "修改密码为：" + password);
				return "logout";
			}
		}
		return "/change_password.jsp";
	}

	@At("/login")
	@Ok("redirect:${obj}")
	public String login(@Param("name") String name, @Param("password") String password) {
		boolean has = UserConfig.has(name, password);
		if (has) {
			setUser(name);
			log.info("用户登录：" + name);
		}
		return "/home";
	}
}
