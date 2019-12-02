package com.softserve.tourcomp.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
  private static volatile DataSource dataSource;

  public static DataSource getDataSource() {

    if (dataSource == null) {
      synchronized (ConnectionPoolHolder.class) {
        if (dataSource == null) {
          BasicDataSource ds = new BasicDataSource();
          ds.setUrl("jdbc:mysql://localhost:8080");
          ds.setUsername("root");
          ds.setPassword("Root1234");
          ds.setMinIdle(5);
          ds.setMaxIdle(10);
          ds.setMaxOpenPreparedStatements(100);
          dataSource = ds;
        }
      }
    }
    return dataSource;

  }
}
