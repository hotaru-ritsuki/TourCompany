package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.country.CountryResponse;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Visas;
import com.softserve.tourcomp.service.inteface.CountryServiceInf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryService implements CountryServiceInf {
  private JDBCDaoFactory daoFactory = new JDBCDaoFactory();
  private CountryDao countryDao = daoFactory.createCountryDao();
  private VisaDao visaDao = daoFactory.createVisaDao();
  private VisaService visaService = new VisaService();

  @Override
  public boolean create(CountryRequest country) {
    Countrys countrys = new Countrys();
    countrys.setName(country.getName());
    countrys.setVisa(visaDao.findById(country.getVisa()).get());
    return countryDao.create(countrys);
  }

  @Override
  public boolean update(Long id, CountryRequest country) {
    Countrys countrys = findOneCountry(id);
    countrys.setName(country.getName());
    countrys.setVisa(visaService.findOneVisa(country.getVisa()));
    return countryDao.update(countrys);
  }

  @Override
  public List<CountryResponse> findAll() {
    List<CountryResponse> list = new ArrayList<>();
    List<Countrys> countrys = countryDao.findAll();
    for (Countrys country : countrys) {
      list.add(countryToCountryResponse(country));
    }
    return list;
  }

  @Override
  public Countrys findOneCountry(Long id) {
    return countryDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not exists"));
  }

  @Override
  public CountryResponse findOne(Long id) {
    Optional<Countrys> byId = countryDao.findById(id);
    if (byId.isPresent()) {
      Countrys country = byId.get();
      return countryToCountryResponse(country);
    }
    new IllegalArgumentException("User with id " + id + " not exists");
    return null;
  }

  protected CountryResponse countryToCountryResponse(Countrys country) {
    CountryResponse cr = new CountryResponse();
    cr.setId(country.getId());
    cr.setName(country.getName());
    Visas visa = country.getVisa();
    if (visa != null) {
      cr.setVisa(visaService.visaToVisaResponse(visa));
    }
    return cr;
  }

}

