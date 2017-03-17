package com.lshb.crawler.crawler;

import com.lshb.crawler.config.data.DataConfig;

public class CrawlerConfig {

  private String name = null;
  private String seedUrl = null;
  private int threadNum = 0;
  private String charset = null;
  private long createTime = 0;

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public static void main(String[] args) {
    CrawlerConfig cc = new CrawlerConfig();
    cc.setCharset("gb2312");
    cc.setName("39健康");
    cc.setCreateTime(System.currentTimeMillis());
    cc.setSeedUrl("http://care.39.net");
    cc.setThreadNum(20);
    DataConfig.toFile(cc);
  }

  public String getSeedUrl() {
    return seedUrl;
  }

  public void setSeedUrl(String seedUrl) {
    this.seedUrl = seedUrl;
  }

  @Override
  public String toString() {
    return "CrawlerConfig [name=" + name + ", seedUrl=" + seedUrl + ", threadNum=" + threadNum + ", charset=" + charset + "]";
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
