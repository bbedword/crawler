package com.lshb.crawler;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.lshb.crawler.server.Bootstrap;
import com.lshb.crawler.server.SiteSetup;
import com.lshb.crawler.util.Config;

@Modules(packages = { "com.lshb.crawler.action" }, scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.lshb" })
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(SiteSetup.class)
public class App {

	public static void main(String[] args) {
		String webPath = "web/"; 
		Config.init(); 
		int port = Config.getInt("server.port", 80);
		Bootstrap.start(webPath, port, "/welcome.jsp");
	}
}
