package com.lshb.crawler.data;

import java.net.MalformedURLException;

import com.lshb.crawler.config.Constant;

public class UrlFilter {

  private FilterType ft;
  private String filter;

  public boolean filter(String url) throws MalformedURLException {
    return ft.value(url, filter);
  }

  public FilterType getFt() {
    return ft;
  }

  public void setFt(FilterType ft) {
    this.ft = ft;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public String toSimpleString() {
    return ft + Constant.CONFIG_PARAM_SPLIT + filter;
  }

  @Override
  public String toString() {
    return "UrlFilter [ft=" + ft + ", filter=" + filter + "]";
  }
}
