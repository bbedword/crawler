package com.lshb.crawler.persist;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.data.DataReg;

public interface Persist {

	void persistData(DataReg dataReg, JSONObject json);

	void persistHtml(DataReg dataReg, JSONObject html);

	boolean validateDate(DataReg dataReg, JSONObject json);
}
