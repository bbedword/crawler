package com.lshb.crawler.data;

import java.net.MalformedURLException;
import java.net.URL;

public enum FilterType {

  REGULAR, // 正则
  START, // 开始
  END; // 结束

  // 返回过滤获得的值
  public boolean value(String url, String filter) throws MalformedURLException {
    switch (this) {
    case REGULAR:
      return url.matches(filter);
    case START:
      return url.startsWith(filter);
    case END:
      return new URL(url).getHost().endsWith(filter);
    default:
      return false;
    }
  }
}
