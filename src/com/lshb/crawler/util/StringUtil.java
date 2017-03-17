package com.lshb.crawler.util;

public class StringUtil {

	public static String upperFirst(String str) {
		if (isBlank(str)) {
			return null;
		}
		char c = Character.toUpperCase(str.charAt(0));
		String substring = str.substring(1);
		return c + substring;
	}

	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0)
			return true;

		int l = str.length();
		for (int i = 0; i < l; i++) {
			if (!isWhitespace(str.codePointAt(i)))
				return false;
		}
		return true;
	}

	public static boolean isWhitespace(int c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
	}
}
