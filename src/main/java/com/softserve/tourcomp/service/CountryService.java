package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.country.CountryResponse;
import com.softserve.tourcomp.dto.visa.VisaResponse;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Visas;

import java.util.Optional;

public class CountryService {
  private JDBCDaoFactory daoFactory=new JDBCDaoFactory();
  private CountryDao countryDao= daoFactory.createCountryDao();
  private VisaDao visaDao = daoFactory.createVisaDao();
  private VisaService visaService=new VisaService();

  public void create(CountryRequest country){
    Countrys countrys=new Countrys();
    countrys.setName(country.getName());
    countrys.setVisa(visaDao.findById(country.getVisa()).get());
    countryDao.create(countrys);
  }

  public Countrys findOneCountry(Long id){
    return countryDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not exists"));
  }

  public CountryResponse findOne(Long id){
    Optional<Countrys> byId = countryDao.findById(id);
    if (byId.isPresent()) {
      Countrys country = byId.get();
      return countryToCountryResponse(country);
    }
    new IllegalArgumentException("User with id " + id + " not exists");
    return null;


  }

  protected CountryResponse countryToCountryResponse(Countrys country){
    CountryResponse cr = new CountryResponse();
    cr.setId(country.getId());
    cr.setName(country.getName());
    Visas visa=country.getVisa();
    if (visa != null){
      cr.setVisa(visaService.visaToVisaResponse(visa));
    }
    return cr;
  }

}

