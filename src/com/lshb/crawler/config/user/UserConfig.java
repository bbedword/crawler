package com.lshb.crawler.config.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lshb.crawler.config.Constant;
import com.lshb.crawler.util.StringUtil;

public class UserConfig {

	private final static Logger log = Logger.getLogger(UserConfig.class);

	private static Map<String, String> users = new HashMap<>();

	public static void init() {
		Map<String, String> mm = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(Constant.USER_FILE))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (StringUtil.isBlank(line) || line.startsWith(Constant.CONFIG_FILE_ANNOTATION)) {
					continue;
				}
				String[] ls = line.split(Constant.CONFIG_PARAM_SPLIT);
				mm.put(ls[0], ls[1]);
			}
			users = mm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("用户配置文件加载完毕，用户数：" + users.size());
	}

	public static boolean has(String user, String pass) {
		String u = users.get(user);
		if (!StringUtil.isBlank(u) && u.equals(pass)) {
			return true;
		}
		return false;
	}

	public static void put(String user, String pass) {
		users.put(user, pass);
	}

	public static boolean writeUser(String user, String pass) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Constant.USER_FILE), true))) {
			bw.write("\n" + user + "=" + pass);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
