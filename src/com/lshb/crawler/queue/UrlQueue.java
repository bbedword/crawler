package com.lshb.crawler.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

//
public class UrlQueue {

	private long urlNum = 0l;
	private List<String> urls = new LinkedList<String>();
	private BloomFilter bf = new BloomFilter();
	private volatile AtomicLong keepNum = new AtomicLong();

	// 向队列尾部追加一个url
	public synchronized boolean add(String url) {
		if (bf.contains(url)) {
			return false;
		}
		bf.add(url);
		urlNum++;
		return urls.add(url);
	}

	public synchronized boolean addNotFilter(String url) {
		urlNum++;
		incrementAndGet();
		return urls.add(url);
	}

	public long incrementAndGet() {
		return keepNum.incrementAndGet();
	}

	public long decrementAndGet() {
		return keepNum.decrementAndGet();
	}

	// 从队列头部中获取一个url
	public synchronized String poll() {
		if (urls.isEmpty()) {
			return null;
		}
		return urls.remove(0);
	}

	// 从队列头部中获取一个url
	public synchronized boolean remove(String url) {
		if (urls.isEmpty()) {
			return true;
		}
		urlNum--;
		return urls.remove(url);
	}

	public synchronized int size() {
		return urls.size();
	}

	public long getUrlNum() {
		return urlNum;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

}
