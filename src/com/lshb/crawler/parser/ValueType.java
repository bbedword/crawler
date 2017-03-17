package com.lshb.crawler.parser;

import java.lang.reflect.InvocationTargetException;

import org.jsoup.nodes.Element;

public enum ValueType {

	TEXT("text"),   // 对应js的text
	INNERHTML("html"),  //对应js的innerHtml
	OUTERHTML("outerHtml"), //对应js的outerHtml
	VALUE("val"); //对应js的value

	private String name;

	private ValueType(String name) {
		this.name = name;
	}

	public String value(Element e) {
		try {
			return (String) e.getClass().getMethod(name).invoke(e);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
