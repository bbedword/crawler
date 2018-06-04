package com.lshb.crawler.request.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.lshb.crawler.config.Constant;

import io.netty.util.internal.ConcurrentSet;

/**
 * --TODO 代理完善，代理ip是否要落地 --QUESTION 是不是应该天黑用国外代理，天明用国内代理？
 * 
 * @author speedy
 *
 */
public class ProxyFromWebManager {

	private final static Logger log = Logger.getLogger(ProxyFromWebManager.class);
	// 改成有序队列
	private static ConcurrentLinkedQueue<ProxyIp> proxys = new ConcurrentLinkedQueue<ProxyIp>();
	private static ConcurrentLinkedQueue<ProxyIpReg> proxyRegs = new ConcurrentLinkedQueue<>();
	private static ConcurrentSet<String> set = new ConcurrentSet<>();
	private static Timer timer = null;
	private static List<ProxyFromWeb> proxyFromWebs = new ArrayList<>();

	public static void start() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				startRun();
			}
		}, 1, 1000 * 60 * 60 * Constant.PROXY_TIMER_INTERNAL);
	}

	public static void startRun() {
		for (ProxyIpReg pir : proxyRegs) {
			ProxyFromWeb proxyFromWeb = new ProxyFromWeb(pir);
			new Thread(proxyFromWeb).start();
			proxyFromWebs.add(proxyFromWeb);
		}
	}

	public static void stop() {
		timer.cancel();
		for (ProxyFromWeb proxyFromWeb : proxyFromWebs) {
			proxyFromWeb.stop();
		}
		proxyFromWebs.clear();
		log.info("proxy获取线程关闭！");
	}

	public static boolean isStoped() {
		return proxyFromWebs.isEmpty();
	}

	/**
	 * 添加代理proxy
	 * 
	 * @param url
	 *            代理页面url
	 * @param m
	 *            代理数据解析规则
	 */
	public static void addProxyReg(ProxyIpReg p) {
		proxyRegs.add(p);
	}

	public static void addProxy(ProxyIp p) {
		if (p == null) {
			return;
		}
		String str = p.toString();
		if (!set.contains(str)) {
			proxys.add(p);
			set.add(str);
//			log.info("新增一个代理：" + str);
		}
	}

	public static int proxySize() {
		return proxys.size();
	}

	public static ProxyIp getProxy(String url) throws MalformedURLException {
		ProxyIp p = proxys.poll();
		while (p.isSealed(new URL(url).getHost())) {
			returnProxy(p);
			try {
				log.info("获得的代理" + p.toString() + "不符合要求，重新获取！");
				Thread.sleep(2 * 1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p = proxys.poll();
		}
		return p;
	}

	public static void returnProxy(ProxyIp p) {
		proxys.add(p);
	}

	public static ConcurrentLinkedQueue<ProxyIp> getProxys() {
		return proxys;
	}

	public static void setProxys(ConcurrentLinkedQueue<ProxyIp> proxys) {
		ProxyFromWebManager.proxys = proxys;
	}

	public static ConcurrentLinkedQueue<ProxyIpReg> getProxyRegs() {
		return proxyRegs;
	}

	public static void setProxyRegs(ConcurrentLinkedQueue<ProxyIpReg> proxyRegs) {
		ProxyFromWebManager.proxyRegs = proxyRegs;
	}

	public static boolean hasProxy() {
		return !proxys.isEmpty();
	}
}
