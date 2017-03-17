package com.lshb.crawler.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.data.UrlFilter;
import com.lshb.crawler.parser.HtmlParser;
import com.lshb.crawler.persist.impl.MongodbPersist;
import com.lshb.crawler.queue.UrlQueue;
import com.lshb.crawler.util.StringUtil;

public class CrawlerGroup {

  private String seedUrl = null;
  private long createTime = System.currentTimeMillis();
  private int threadNum = Constant.CRAWLER_THREAD_NUM;
  private UrlQueue urlQueue = new UrlQueue();
  private List<Crawler> crawlers = new ArrayList<>();
  private MongodbPersist mongodbPersist = new MongodbPersist();
  private HtmlParser parser = new HtmlParser();
  private String charset = "utf-8";
  private String name;

  public String getName() {
    return name;
  }

  public void stop() {
    for (Crawler crawler : crawlers) {
      crawler.stop();
    }
    crawlers.clear();
  }

  public boolean isStoped() {
    return crawlers.isEmpty();
  }

  public CrawlerGroup(CrawlerConfig cc) {
    if (cc != null) {
      if (cc.getThreadNum() > 0) {
        this.threadNum = cc.getThreadNum();
      }
      if (!StringUtil.isBlank(cc.getCharset())) {
        this.charset = cc.getCharset();
      }
      if (!StringUtil.isBlank(cc.getName())) {
        this.name = cc.getName();
      }
      if (!StringUtil.isBlank(cc.getSeedUrl())) {
        this.seedUrl = cc.getSeedUrl();
        addUrl(seedUrl);
      }
      if (cc.getCreateTime() > 0) {
        this.createTime = cc.getCreateTime();
      }
    }
  }

  /**
   * 添加爬虫种子
   * @param url
   */
  public void addUrl(String... url) {
    for (String u : url) {
      urlQueue.add(u);
    }
  }

  /**
   * 爬虫开始方法
   * @param url 种子url
   */
  public void start() {
    for (int i = 0; i < threadNum; i++) {
      Crawler crawler = new Crawler(urlQueue, mongodbPersist, parser, charset);
      new Thread(crawler).start();
      crawlers.add(crawler);
    }
    //监控线程是否已经爬取完毕
    while (true) {
      if (urlQueue.size() == 0 && allSleep()) {
        stop();
        break;
      }
      try {
        Thread.sleep(60 * 1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // 所有线程是否都休眠
  public boolean allSleep() {
    for (Crawler crawler : crawlers) {
      if (!crawler.isSleeping()) {
        return false;
      }
    }
    return true;
  }

  public void addCrawler(int num) {
    for (int i = 0; i < num; i++) {
      Crawler crawler = new Crawler(urlQueue, mongodbPersist, parser, charset);
      new Thread(crawler).start();
      crawlers.add(crawler);
    }
  }

  public void subCrawler(int num) {
    for (int i = 0; i < num; i++) {
      Crawler crawler = crawlers.remove(0);
      if (crawler != null) {
        crawler.stop();
      }
    }
  }

  public void addDataReg(DataReg dr) {
    parser.addDataReg(dr);
  }

  public DataReg rmDataReg(String urlReg) {
    return parser.rmDataReg(urlReg);
  }

  public void addUrlFilterReg(UrlFilter uf) {
    parser.addFilterReg(uf);
  }

  public UrlFilter rmUrlFilterReg(String filterReg) {
    return parser.rmFilterReg(filterReg);
  }

  public DataReg getDataReg(String urlReg) {
    return parser.getDataReg(urlReg);
  }

  public void addUrl(String url) {
    urlQueue.add(url);
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public UrlQueue getUrlQueue() {
    return urlQueue;
  }

  public void setUrlQueue(UrlQueue urlQueue) {
    this.urlQueue = urlQueue;
  }

  public Date getCreateTime() {
    return new Date(createTime);
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public HtmlParser getParser() {
    return parser;
  }

  public String getSeedUrl() {
    return seedUrl;
  }

  public void setSeedUrl(String seedUrl) {
    this.seedUrl = seedUrl;
  }

  public List<Crawler> getCrawlers() {
    return crawlers;
  }

  public void setCrawlers(List<Crawler> crawlers) {
    this.crawlers = crawlers;
  }

}
