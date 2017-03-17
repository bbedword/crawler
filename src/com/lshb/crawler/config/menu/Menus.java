package com.lshb.crawler.config.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.Constant;
import com.lshb.crawler.util.StringUtil;

public class Menus {

  private static List<JSONObject> ms = new ArrayList<>();

  public static void init() {
    List<JSONObject> list = new ArrayList<>();
    File file = new File(Constant.MENU_CONFIG_FILE);
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line = null;
      while ((line = br.readLine()) != null) {
        if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
          continue;
        }
        JSONObject json = loadLine(line);
        if (json != null) {
          list.add(json);
        }
      }
      ms = list;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static JSONObject loadLine(String line) {
    String[] ls = line.split(Constant.CONFIG_PARAM_SPLIT);
    JSONObject json = new JSONObject();
    json.put("id", Integer.valueOf(ls[0]));
    json.put("name", ls[1]);
    json.put("icon", ls[2]);
    json.put("url", ls[3]);
    json.put("parent_id", Integer.valueOf(ls[4]));
    return json;
  }

  public static List<JSONObject> getMs() {
    return ms;
  }

  public static void setMs(List<JSONObject> ms) {
    Menus.ms = ms;
  }
}
