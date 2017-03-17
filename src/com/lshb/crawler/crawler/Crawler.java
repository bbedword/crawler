package com.lshb.crawler.crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.Constant;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.parser.HtmlParser;
import com.lshb.crawler.persist.Persist;
import com.lshb.crawler.queue.UrlQueue;
import com.lshb.crawler.request.Http;
import com.lshb.crawler.util.RandomUtil;
import com.lshb.crawler.util.StringUtil;

public class Crawler implements Runnable {

  private final static Logger log = Logger.getLogger(Crawler.class);
  private UrlQueue urlQueue = null;
  private Persist persist = null;
  private HtmlParser parser = null;
  private boolean isRunning = true;
  private boolean isSleeping= false;
  private Http http = new Http();
  private String charset = null;
  private String url = null;

  public Crawler(UrlQueue urlQueue, Persist persist, HtmlParser parser, String charset) {
    this.urlQueue = urlQueue;
    this.persist = persist;
    this.parser = parser;
    this.charset = charset;
  }

  @Override
  public void run() {
    isRunning = true;
    while (isRunning) {
      sleepRandomTime();
      url = urlQueue.poll();
      // 从队列中获取url
      if (StringUtil.isBlank(url)) {
        sleepRandomTime();
        continue;
      }
      Response res = null;
      try {
        log.info(Thread.currentThread().getName() + "开始爬取url:" + url);
        res = http.getWithProxy(url);
      } catch (Exception e) {
        if (dealException(url, e)) {
          continue;
        }
        // 代理用完后报null指针
        if (e instanceof NullPointerException) {
          if (http.getProxy() == null) {
            urlQueue.addNotFilter(url);
            continue;
          }
        }
      }
      int code = res.statusCode();
      if (code > 200 && code < 400) {
        log.error(code + ",url:" + url);
        continue;
      }
      byte[] bytes = res.bodyAsBytes();
      String charset = res.charset() == null ? this.charset : res.charset();

      try {
        String html = new String(bytes, charset);
        // 将html中的url抽取出来，放到url队列中
        List<String> extraUrl = parser.extraUrl(html);
        for (String string : extraUrl) {
          urlQueue.add(string);
        }
        // 获得指定url的页面模板
        DataReg dataReg = parser.getDataRegFromUrl(url);
        if (dataReg == null) {
          continue;
        }
        if (dataReg.isOk(html)) {
          if (dataReg.getIsStoreHtml()) {
            persistHtml(url, html, dataReg);
          }
          persistData(url, html, dataReg);
          log.info("[persist] " + Thread.currentThread().getName() + ":" + url);
        }
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
  }

  private void sleepRandomTime() {
    isSleeping = true;
    try {
      // 爬虫随机休眠一段时间
      Thread.sleep(RandomUtil.oneInRange(Constant.CRAWLER_THREAD_SLEEP_MIN, Constant.CRAWLER_THREAD_SLEEP_MAX));
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    isSleeping = false;
  }

  // 处理异常
  private boolean dealException(String url, Exception e) {
    if (e instanceof MalformedURLException) {
      log.error("错误协议头url：" + url, e);
      return true;
    }
    if (e instanceof UnsupportedMimeTypeException) {
      log.error("错误的MimiType类型url：" + url, e);
      return true;
    }
    if (e instanceof HttpStatusException) {
      int code = ((HttpStatusException) e).getStatusCode();
      if (code == 503 || code == 504 || code == 500) {
        // 重新分配代理
        http.allotProxy(url);
        // 将未爬取的url放回url队列中
        urlQueue.addNotFilter(url);
      }
      log.error(code + ",url：" + url, e);
      return true;
    }
    if (e instanceof SocketTimeoutException || e instanceof IOException) {
      // 重新分配代理
      http.allotProxy(url);
      // 将未爬取的url放回url队列中
      urlQueue.addNotFilter(url);
      log.error("SocketTimeout url：" + url, e);
      return true;
    }
    return false;
  }

  private JSONObject persistData(String url, String html, DataReg dataReg) {
    // 将html中的数据抽取出来
    JSONObject data = HtmlParser.extraData(dataReg, html);
    data.put("_id", url);
    persist.persistData(dataReg, data);
    return data;
  }

  private JSONObject persistHtml(String url, String html, DataReg dataReg) {
    // 将html包装成json
    JSONObject ht = new JSONObject();
    ht.put("_id", url);
    ht.put("html", html);
    persist.persistHtml(dataReg, ht);
    return ht;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void stop() {
    log.info(Thread.currentThread().getName() + "线程关闭!");
    this.isRunning = false;
  }

  @Override
  public String toString() {
    return http.toString();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isSleeping() {
    return isSleeping;
  }

  public void setSleeping(boolean isSleeping) {
    this.isSleeping = isSleeping;
  }

  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }
}
