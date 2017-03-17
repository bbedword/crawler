package com.lshb.crawler.request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import com.lshb.crawler.request.proxy.ProxyFromWebManager;
import com.lshb.crawler.request.proxy.ProxyIp;

import org.jsoup.Jsoup;

public class Http {

  private final static Logger log = Logger.getLogger(Http.class);
  private String userAgent = null;
  private ProxyIp proxy = null;
  private int timeout = 60 * 1000;

  public Http() {
    userAgent = UserAgent.getUserAgent();
  }

  public Response get(String url) throws IOException {
    Connection con = Jsoup.connect(url).userAgent(userAgent);
    return con.timeout(timeout).execute();
  }

  public Response getWithProxy(String url) throws IOException {
    Connection con = Jsoup.connect(url).userAgent(userAgent);
    if (proxy == null || proxy.isNeedChange()) {
      proxy = ProxyFromWebManager.getProxy(url);
      log.info(Thread.currentThread().getName() + "申请代理为：" + proxy);
      con.proxy(new Proxy(Type.HTTP, new InetSocketAddress(proxy.getIp(), proxy.getPort())));
    }
    return con.timeout(timeout).execute();
  }

  public void allotProxy(String url) {
    if (proxy != null) {
      try {
        proxy.addFailHost(new URL(url).getHost());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      ProxyFromWebManager.returnProxy(proxy);
    }
    proxy = null;
  }

  public ProxyIp getProxy() {
    return proxy;
  }

  public Http proxy(ProxyIp proxy) {
    this.proxy = proxy;
    return this;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public Http userAgent(String userAgent) {
    this.userAgent = userAgent;
    return this;
  }

  public int getTimeout() {
    return timeout;
  }

  public Http timeOut(int timeout) {
    this.timeout = timeout;
    return this;
  }

  @Override
  public String toString() {
    if (proxy == null) {
      return "";
    }
    return proxy.toString();
  }
}
