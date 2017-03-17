package com.lshb.crawler.persist.impl;

import com.alibaba.fastjson.JSONObject;
import com.lshb.crawler.data.DataReg;
import com.lshb.crawler.persist.Persist;

// 将数据持久化到文件中
public class FilePersist implements Persist {

  @Override
  public boolean validateDate(DataReg dataReg, JSONObject json) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void persistData(DataReg dataReg, JSONObject json) {
    // TODO Auto-generated method stub

  }

  @Override
  public void persistHtml(DataReg dataReg, JSONObject html) {
    // TODO Auto-generated method stub

  }
}
