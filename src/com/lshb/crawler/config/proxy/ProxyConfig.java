package com.lshb.crawler.config.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nutz.json.Json;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.request.UserAgent;
import com.lshb.crawler.request.proxy.ProxyFromWebManager;
import com.lshb.crawler.request.proxy.ProxyIp;
import com.lshb.crawler.request.proxy.ProxyIpReg;
import com.lshb.crawler.util.Config;
import com.lshb.crawler.util.StringUtil;

/**
 * 负责代理解析配置文件的加载
 * @author speedy
 */
public class ProxyConfig {

  private final static Logger log = Logger.getLogger(ProxyConfig.class);

  public static void init() {
    File[] files = new File(Constant.PROXY_DIR).listFiles();
    for (File f : files) {
      // 加载网络proxy配置文件
      String fileName = f.getName();
      if (fileName.startsWith(Constant.PRE_WEB_PROXY_CONFIG_FILE)) {
        initWebProxy(f);
      }
      // 加载本地proxy配置文件
      if (fileName.startsWith(Constant.PRE_LOCAL_PROXY_CONFIG_FILE)) {
        initLocalProxy(f);
      }
      // 加载本地ua配置文件
      if (fileName.startsWith(Constant.PRE_UA_CONFIG_FILE)) {
        initLocalUserAgent(f);
      }
    }
    log.info("代理配置文件加载完毕，代理数：" + ProxyFromWebManager.proxySize() + "，userAgent数：" + UserAgent.size());
  }

  private static void initLocalUserAgent(File f) {
    try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
      String line = null;
      while ((line = bfr.readLine()) != null) {
        if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
          continue;
        }
        UserAgent.add(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void initLocalProxy(File f) {
    try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
      String line = null;
      while ((line = bfr.readLine()) != null) {
        if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
          continue;
        }
        String[] split = line.split("\\:");
        ProxyIp p = new ProxyIp();
        p.setIp(split[0]);
        p.setPort(Integer.parseInt(split[1]));
        if (p.isOk(Constant.PROXY_VALIDATE_URL, Constant.PROXY_VALIDATE_CONTENT, Constant.PROXY_VALIDATE_CONTENT_CHARSET)) {
          ProxyFromWebManager.addProxy(p);
          log.info("添加一个代理：" + p.toString());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void initWebProxy(File f) {
    try (FileReader fr = new FileReader(f)) {
      ProxyIpReg ps = Json.fromJson(ProxyIpReg.class, fr);
      ProxyFromWebManager.addProxyReg(ps);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void toFile(ProxyIpReg pir, File file) {
    Json.toJsonFile(file, pir);
  }

  public static void main(String[] args) {
    Config.init();
    ProxyIpReg pir = new ProxyIpReg();
    pir.setUrl("http://www.kuaidaili.com/free/");
    pir.setLine("#list > table > tbody > tr");
    pir.setIp("td:nth-child(1)");
    pir.setPort("td:nth-child(2)");
    pir.setAddress("td:nth-child(5)");
    pir.setAnonymity("td:nth-child(3)");
    pir.setType("td:nth-child(4)");
    pir.setVerifyTime("td:nth-child(7)");
    List<ProxyIpReg> list = new ArrayList<>();
    list.add(pir);
    list.add(pir);
    File file = new File(Constant.PROXY_DIR + "/test.txt");
    Json.toJsonFile(file, list);
  }
}
