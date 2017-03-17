/**
 * @Copyright 2015
 *
 **/
package com.lshb.crawler.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author speedy
 * @createTime 2015年9月1日
 */
public class ActionResult {

	private boolean ok;
	private String message = "";
	private JSONObject json = null;

	private ActionResult(boolean ok, String message, JSONObject json) {
		this.ok = ok;
		this.message = message;
		this.json = json;
	}

	public static ActionResult ok(String message, JSONObject json) {
		return new ActionResult(true, message, json);
	}

	public static ActionResult ok(String message) {
		return new ActionResult(true, message, null);
	}

	public static ActionResult ok() {
		return new ActionResult(true, null, null);
	}

	public static ActionResult fail(String message) {
		return new ActionResult(false, message, null);
	}

	public static ActionResult fail(String message, JSONObject json) {
		return new ActionResult(false, message, json);
	}

	public static ActionResult fail() {
		return new ActionResult(false, null, null);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
