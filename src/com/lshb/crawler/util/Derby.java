package com.lshb.crawler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Derby {

  private static Logger log = Logger.getAnonymousLogger();

  private static String embeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
  private static String networkDriver = "org.apache.derby.jdbc.ClientDriver";
  private static String protocol = "jdbc:derby:";
  
  private Connection connection = null;
  private String url = null;
  private boolean isNetworkDB = false;

  static{
    //加载derby两种驱动
    loadDriver(false);
//    loadDriver(true);
  }
  
  public static void loadDriver(boolean isNetworkDB) {
    String driver = isNetworkDB ? networkDriver : embeddedDriver;
    try {
      Class.forName(driver).newInstance();
      log.info("加载derby数据库驱动成功！");
      return;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    log.info("加载derby数据库驱动失败！");
  }

  /**
   * 初始化一个网络的derby
   * @param ip
   * @param port
   * @param dbName
   */
  public Derby(String ip, int port, String dbName) {
    this.isNetworkDB = true;
    if (connection == null) {
      url = protocol + "//" + ip + ":" + port + "/" + dbName + ";";
      try {
        connection = DriverManager.getConnection(url + "create=true");
      } catch (SQLException e) {
        e.printStackTrace();
        log.info("初始化数据库连接失败！" + e.getMessage());
      }
    }
  }

  /**
   * 初始化一个内嵌的derby
   * @param dbName
   */
  public Derby(String dbName) {
    if (connection == null) {
      url = protocol + dbName + ";";
      try {
        connection = DriverManager.getConnection(url + "create=true");
      } catch (SQLException e) {
        e.printStackTrace();
        log.info("初始化数据库连接失败！" + e.getMessage());
      }
    }
  }

  /**
   * 建表
   * @param sql
   * @return
   */
  public boolean execute(String sql) {
    Statement s = null;
    try {
      s = connection.createStatement();
      return s.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 查询数据，包括select
   * @param sql
   * @return
   */
  public ResultSet select(String sql) {
    Statement s = null;
    try {
      s = connection.createStatement();
      return s.executeQuery(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        try {
          s.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * 更新数据库数据，包括insert，update，delete
   * @param sql
   * @return
   */
  public int update(String sql) {
    Statement s = null;
    try {
      s = connection.createStatement();
      return s.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (s != null) {
        try {
          s.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return 0;
  }

  /**
   * 关闭derby连接
   */
  public void shutDown() {
    try {
      DriverManager.getConnection(url + "shutdown=true");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isNetworkDB() {
    return isNetworkDB;
  }

  public void setNetworkDB(boolean isNetworkDB) {
    this.isNetworkDB = isNetworkDB;
  }

}
