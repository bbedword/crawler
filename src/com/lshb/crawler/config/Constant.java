package com.lshb.crawler.config;

import com.lshb.crawler.util.Config;

public class Constant {

	// 代理验证url
	public static String PROXY_VALIDATE_URL = Config.getString("proxy.validate.url", "http://www.baidu.com");
	// 代理验证内容
	public static String PROXY_VALIDATE_CONTENT = Config.getString("proxy.validate.content", "百度一下");
	// 验证内容编码方式
	public static String PROXY_VALIDATE_CONTENT_CHARSET = Config.getString("proxy.validate.content.charset",
			"utf-8");
	// 配置文件中的注释符
	public static String CONFIG_FILE_ANNOTATION = "#";
	// userAgent
	public static String PRE_UA_CONFIG_FILE = "ua_";
	// web代理
	public static String PRE_WEB_PROXY_CONFIG_FILE = "web_";
	// 本地代理
	public static String PRE_LOCAL_PROXY_CONFIG_FILE = "local_";
	public static String PROXY_URL_PAGE_CHAR = "@";
	
	public static String PROXY_DIR = Config.getString("proxy.config.dir", "config/proxy");
	public static int PROXY_TIMER_INTERNAL = Config.getInt("proxy.timer.internal", 6);
	public static int PROXY_FAILED_TIMES = Config.getInt("proxy.failed.times", 3);

	// 数据配置
	public static String DATA_PRE_CONFIG_FILE = "data_";
	public static String DATA_STORE_MONGO_URI = Config.getString("data.store.mongo.uri");
	public static String DATA_CONFIG_DIR = Config.getString("data.config.dir", "config/data");
	public static String DATA_PARSER_ERROR_STR = "PARSER_ERROR";

	// 目录配置
	public static String MENU_CONFIG_FILE = Config.getString("menu.config.file", "config/menu.txt");
	public static String CONFIG_PARAM_SPLIT = "\t";
	// 用户配置文件中的注释符
	public static String USER_FILE = Config.getString("user.config.file", "config/user.txt");

	// 爬取线程数
	public static int CRAWLER_THREAD_NUM = Config.getInt("crawler.thread.num", 20);
	public static int CRAWLER_THREAD_SLEEP_MIN = Config.getInt("crawler.thread.sleep.min", 2);
	public static int CRAWLER_THREAD_SLEEP_MAX = Config.getInt("crawler.thread.sleep.max", 10);

	// 爬取url
	public static String URL_USERDEFINE_DIR = Config.getString("url.userdefine.add.dir", "config/url/url.txt");

}
