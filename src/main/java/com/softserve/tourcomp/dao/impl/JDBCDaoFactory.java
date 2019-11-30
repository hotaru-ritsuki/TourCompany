package com.softserve.tourcomp.dao.impl;
import com.softserve.tourcomp.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

  private DataSource dataSource = ConnectionPoolHolder.getDataSource();

  @Override
  public BookingDao createBookingDao() {
    return new JDBCSBookingDao(getConnection());
  }

  @Override
  public UserDao createUserDao() {
    return new JDBCUserDao(getConnection());
  }

  @Override
  public HotelDao createHotelDao() {
    return new JDBCHotelDao(getConnection());
  }

  @Override
  public VisaDao createVisaDao() {
    return new JDBCVisaDao(getConnection());
  }

  @Override
  public CityDao createCityDao() {
    return new JDBCCityDao(getConnection());
  }

  @Override
  public CountryDao createCountryDao() {
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
