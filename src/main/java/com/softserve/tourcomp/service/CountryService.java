package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.country.CountryResponse;
import com.softserve.tourcomp.entity.Countrys;

public class CountryService {
  private JDBCDaoFactory daoFactory=new JDBCDaoFactory();
  private CountryDao countryDao= daoFactory.createCountryDao();
  private VisaDao visaDao = daoFactory.createVisaDao();

  public void create(CountryRequest country){
    Countrys countrys=new Countrys();
    countrys.setName(country.getName());
    countrys.setVisa(visaDao.findById(country.getVisa()).get());
    countryDao.create(countrys);
  }

  public void findOneCountry(Long id){

  }
  private CountryResponse countryToCountryResponse(){
    CountryResponse cr = new CountryResponse();

    return cr;
  }

}

