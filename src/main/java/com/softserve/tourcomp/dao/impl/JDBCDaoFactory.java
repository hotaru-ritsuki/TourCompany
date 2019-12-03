package com.softserve.tourcomp.dao.impl;
import com.softserve.tourcomp.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

  private DataSource dataSource = ConnectionPoolHolder.getDataSource();

  @Override
  public JDBCBookingDao createBookingDao() {
    return new JDBCBookingDao(getConnection());
  }

  @Override
  public JDBCUserDao createUserDao() {
    return new JDBCUserDao(getConnection());
  }

  @Override
  public JDBCHotelDao createHotelDao() {
    return new JDBCHotelDao(getConnection());
  }

  @Override
  public JDBCVisaDao createVisaDao() {
    return new JDBCVisaDao(getConnection());
  }

  @Override
  public JDBCCityDao createCityDao() {
    return new JDBCCityDao(getConnection());
  }

  @Override
  public JDBCCountryDao createCountryDao() {
    return new JDBCCountryDao(getConnection());
  }

  private Connection getConnection(){
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
