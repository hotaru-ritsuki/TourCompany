package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class JDBCDaoFactory extends DaoFactory {

  private DataSource dataSource = ConnectionPoolHolder.getDataSource();

  /**
   * @return
   */
  @Override
  public JDBCBookingDao createBookingDao() {
    return new JDBCBookingDao(getConnection());
  }

  /**
   * @return
   */
  @Override
  public JDBCUserDao createUserDao() {
    return new JDBCUserDao(getConnection());
  }

  /**
   * @return
   */
  @Override
  public JDBCHotelDao createHotelDao() {
    return new JDBCHotelDao(getConnection());
  }

  /**
   * @return
   */
  @Override
  public JDBCVisaDao createVisaDao() {
    return new JDBCVisaDao(getConnection());
  }

  /**
   * @return
   */
  @Override
  public JDBCCityDao createCityDao() {
    return new JDBCCityDao(getConnection());
  }

  /**
   * @return
   */
  @Override
  public JDBCCountryDao createCountryDao() {
    return new JDBCCountryDao(getConnection());
  }

  /**
   * @return
   */
  private Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
