package com.lshb.crawler.manager;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.lshb.crawler.crawler.CrawlerConfig;
import com.lshb.crawler.crawler.CrawlerGroup;
import com.lshb.crawler.request.proxy.ProxyFromWebManager;

/**
 * 可以添加定时启动任务
 * 
 * @author speedy
 *
 */
public class CrawlerManager {

	private final static Logger log = Logger.getLogger(CrawlerManager.class);
	private static HashMap<String, CrawlerGroup> maps = new HashMap<>();

	public static synchronized CrawlerGroup createCrawlerGroup(CrawlerConfig cc) {
		CrawlerGroup cg = new CrawlerGroup(cc);
		maps.put(cg.getName(), cg);
		return cg;
	}

	public static synchronized CrawlerGroup getCrawlerGroup(String name) {
		return maps.get(name);
	}

	public static synchronized CrawlerGroup removeCrawlerGroup(String name) {
		return maps.remove(name);
	}

	public static synchronized boolean hasName(String name) {
		return maps.containsKey(name);
	}

	/**
	 * 开始某个爬虫组
	 * 
	 * @param name
	 */
	public static synchronized void start(String name) {
		CrawlerGroup cg = maps.get(name);
		if (cg != null) {
			if (ProxyFromWebManager.isStoped()) {
				ProxyFromWebManager.start();
				while (!ProxyFromWebManager.hasProxy()) {
					try {
						Thread.sleep(3 * 1000l);
						log.info("还没有代理存在，等待3秒钟！");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			cg.start();
		}
	}

	/**
	 * 停止某个爬虫组
	 * 
	 * @param name
	 */
	public static synchronized void stop(String name) {
		CrawlerGroup cg = maps.get(name);
		if (cg != null) {
			cg.stop();
			try {
				Thread.sleep(10 * 1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (allStop()) {
				ProxyFromWebManager.stop();
			}
		}
	}

	/**
	 * 所有爬虫是否停止
	 * 
	 * @return
	 */
	public static boolean allStop() {
		for (CrawlerGroup cg : maps.values()) {
			if (!cg.isStoped()) {
				return false;
			}
		}
		return true;
	}

	public static synchronized int size() {
		return maps.size();
	}

	public static synchronized HashMap<String, CrawlerGroup> getMaps() {
		return maps;
	}
}
