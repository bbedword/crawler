package com.lshb.crawler.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UserAgent {

	private static List<String> userAgents = Collections.synchronizedList(new ArrayList<String>());

	public static void add(String userAgent) {
		userAgents.add(userAgent);
	}

	public static String getUserAgent() {
		int size = userAgents.size();
		if (size == 0) {
			return null;
		}
		Random r = new Random();
		int nextInt = r.nextInt(size);
		return userAgents.get(nextInt);
	}

	public static void resetUserAgents(List<String> list) {
		userAgents = Collections.synchronizedList(list);
	}

	public static int size() {
		return userAgents.size();
	}
}
