package com.lshb.crawler.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ValueReg {

	private String reg;
	private ValueType vt = ValueType.TEXT;

	public ValueReg() {
	}

	public ValueReg(String reg, ValueType vt) {
		this.reg = reg;
		this.vt = vt;
	}

	public ValueReg(String reg) {
		this.reg = reg;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getVt() {
		return vt.name();
	}

	public void setVt(String vt) {
		this.vt = ValueType.valueOf(vt);
	}

	public String getValue(Document doc) {
		Element e = doc.select(reg).get(0);
		return vt.value(e);
	}

	@Override
	public String toString() {
		return "ValueReg [reg=" + reg + ", vt=" + vt + "]";
	}

}
