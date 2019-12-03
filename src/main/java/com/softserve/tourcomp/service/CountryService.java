package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;

public class CountryService {
  private JDBCDaoFactory daoFactory=new JDBCDaoFactory();
  private CountryDao countryDao= daoFactory.createCountryDao();

  public void create(){


  }

}

