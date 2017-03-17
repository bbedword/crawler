package com.taogu.crawler;

import org.nutz.lang.Files;

public class Test {

	public static void main(String[] args) {
		String path = "C:\\Users\\shb\\Desktop\\listRaw.txt";
		String str = Files.read(path);
		String[] ss = str.split("\",\"");
		StringBuffer sb = new StringBuffer();
		for (String string : ss) {
			sb.append(string);
			sb.append("\n");
		}
		String path1 = "C:\\Users\\shb\\Desktop\\listRaw1.txt";
		Files.write(path1, sb);
	}
}
