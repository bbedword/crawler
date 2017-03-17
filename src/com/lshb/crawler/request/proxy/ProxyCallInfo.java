package com.lshb.crawler.request.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.lshb.crawler.config.Constant;

public class ProxyCallInfo {

	private Map<String, AtomicInteger> errorInfo = new HashMap<>();

	public boolean isSealed(String host) {
		if (getNum(host) > Constant.PROXY_FAILED_TIMES) {
			return true;
		}
		return false;
	}

	public int getNum(String host) {
		AtomicInteger ai = errorInfo.get(host);
		if (ai != null) {
			return ai.intValue();
		}
		return 0;
	}

	public int addFailHost(String host) {
		AtomicInteger ai = errorInfo.get(host);
		if (ai == null) {
			ai = new AtomicInteger(1);
			errorInfo.put(host, ai);
			return ai.intValue();
		} else {
			return ai.incrementAndGet();
		}
	}

	public AtomicInteger rmHost(String host) {
		return errorInfo.remove(host);
	}

	public boolean needChange() {
		// --TODO 判断代理是否需要更换
		// 30s内访问网站多少次，1m内访问多少次，1h访问多少次，1d多少次
		// 上面几个条件不一定全部满足

		return false;
	}
}
