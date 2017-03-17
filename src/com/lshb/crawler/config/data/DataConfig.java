package com.lshb.crawler.config.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.crawler.CrawlerConfig;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.data.FilterType;
import com.lshb.crawler.data.UrlFilter;
import com.lshb.crawler.manager.CrawlerManager;
import com.lshb.crawler.util.StringUtil;

public class DataConfig {

  private final static Logger log = Logger.getLogger(DataConfig.class);
  // 配置文件中的注释符

  private static Map<String, String> map = new HashMap<>();

  public static void init() {
    File[] files = new File(Constant.DATA_CONFIG_DIR).listFiles();
    for (File f : files) {
      if (f.isDirectory()) {
        String name = f.getName();
        // 任务文件
        File f1 = new File(f, name);
        initTaskFile(f1);
        // 过滤模板文件
        File f2 = new File(f, name + ".filter");
        initTemplateFile(name, f2);
        // 数据模板文件加载
        initDataFile(f, name);
      }
    }
    log.info("爬虫配置文件加载完毕，总共爬虫数：" + CrawlerManager.size());
  }

  // 加载数据文件
  private static void initDataFile(File f, String name) {
    File[] fs = f.listFiles();
    for (File file : fs) {
      // 数据文件以data开头
      if (file.getName().startsWith("data")) {
        try (FileReader fr = new FileReader(file)) {
          DataReg dr = Json.fromJson(DataReg.class, fr);
          CrawlerManager.getCrawlerGroup(name).addDataReg(dr);
          map.put(dr.getUrlReg(), file.getAbsolutePath());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    log.info("完成数据文件的加载！");
  }

  private static void initTemplateFile(String name, File f2) {
    if (f2.exists()) {
      try (BufferedReader br = new BufferedReader(new FileReader(f2))) {
        String line = null;
        while ((line = br.readLine()) != null) {
          if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
            continue;
          }
          String[] strs = line.split(Constant.CONFIG_PARAM_SPLIT);
          UrlFilter uf = new UrlFilter();
          uf.setFt(FilterType.valueOf(strs[0].trim()));
          uf.setFilter(strs[1].trim());
          CrawlerManager.getCrawlerGroup(name).addUrlFilterReg(uf);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    log.info("完成模版文件的加载！");
  }

  // 加载任务文件
  private static void initTaskFile(File f1) {
    if (f1.exists()) {
      try (FileReader fr = new FileReader(f1)) {
        CrawlerConfig cc = Json.fromJson(CrawlerConfig.class, fr);
        CrawlerManager.createCrawlerGroup(cc);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    log.info("完成任务文件的加载！");
  }

  public static synchronized String toFile(String crawler, DataReg dr) {
    File file = new File(Constant.DATA_CONFIG_DIR);
    String name = null;
    String urlReg = dr.getUrlReg();
    File f = null;
    if (map.containsKey(urlReg)) {
      name = map.get(urlReg);
      f = new File(name);
    } else {
      name = Constant.DATA_PRE_CONFIG_FILE + System.currentTimeMillis() + ".txt";
      f = new File(file, crawler + File.separator + name);
    }
    Json.toJsonFile(f, dr);
    String path = f.getAbsolutePath();
    map.put(urlReg, path);
    return path;
  }

  public static synchronized String toFile(String crawler, List<UrlFilter> filters) {
    File file = new File(Constant.DATA_CONFIG_DIR);
    String name = crawler + File.separator + crawler + ".filter";
    File f = new File(file, name);
    StringBuffer sb = new StringBuffer();
    for (UrlFilter urlFilter : filters) {
      sb.append(urlFilter.toSimpleString());
      sb.append("\n");
    }
    Files.write(f, sb.toString());
    String path = f.getAbsolutePath();
    map.put(name, path);
    return path;
  }

  public static synchronized String toFile(CrawlerConfig cc) {
    File file = new File(Constant.DATA_CONFIG_DIR);
    String crawler = cc.getName();
    File f = new File(file, crawler + File.separator + crawler);
    Json.toJsonFile(f, cc);
    String path = f.getAbsolutePath();
    map.put(crawler, path);
    return path;
  }

  public static synchronized String rmFile(String urlReg) {
    String filePath = map.remove(urlReg);
    if (!StringUtil.isBlank(filePath)) {
      File file = new File(filePath);
      file.delete();
    }
    return filePath;
  }
}
