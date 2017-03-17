package com.lshb.crawler.persist.impl;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.config.Constant;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.persist.Persist;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongodbPersist implements Persist {

	private final static Logger log = Logger.getLogger(MongodbPersist.class);
	private static MongoClient client = new MongoClient(new MongoClientURI(Constant.DATA_STORE_MONGO_URI));

	@Override
	public void persistData(DataReg reg, JSONObject json) {
		log.info("落地的数据：" + json.toJSONString());
		String db = reg.getDb();
		MongoDatabase database = client.getDatabase(db);
		String dataTable = reg.getDataTable();
		// 验证data解析没有问题，落地
		if (validateDate(reg, json)) {
			Document doc = Document.parse(json.toJSONString());
			database.getCollection(dataTable).insertOne(doc);
		}
	}

	@Override
	public void persistHtml(DataReg reg, JSONObject html) {
		String db = reg.getDb();
		MongoDatabase database = client.getDatabase(db);
		String htmlTable = reg.getHtmlTable();
		Document ht = Document.parse(html.toJSONString());
		database.getCollection(htmlTable).insertOne(ht);
	}

	@Override
	public boolean validateDate(DataReg dataReg, JSONObject json) {
		// TODO 验证数据是否完整
		// QUESTION 数据不完整时是否也落地?

		return true;
	}

}
