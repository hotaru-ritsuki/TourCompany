package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CityDao;
import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.city.CityRequest;
import com.softserve.tourcomp.dto.city.CityResponse;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityService {
  private JDBCDaoFactory daoFactory = new JDBCDaoFactory();
  private CityDao cityDao = daoFactory.createCityDao();
  private CountryDao countryDao = daoFactory.createCountryDao();

  public boolean create(CityRequest cityRequest) {
    Citys city = new Citys();
    city.setName(cityRequest.getName());
    city.setCountry(countryDao.findById(cityRequest.getCountry()).get());
    return cityDao.create(city);
  }

  public Citys findOneCity(Long id) {
    return cityDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not exists"));
  }

  public CityResponse findOne(Long id) {
    try {
      Optional<Citys> byId = cityDao.findById(id);
      Citys citys = byId.get();
      return cityToCityResponse(citys);
    } catch (Exception e) {
      throw new IllegalArgumentException("User with id " + id + " not exists");
    }
  }

  public List<CityResponse> findAll() {
    try {
      List<CityResponse> list = new ArrayList<>();
      List<Citys> citys = cityDao.findAll();
      for (Citys city : citys) {
        list.add(cityToCityResponse(city));
      }
      return list;
    } catch (Exception e) {
      throw new NullPointerException();
    }
  }

  private CityResponse cityToCityResponse(Citys city) {
    CityResponse cr = new CityResponse();
    cr.setId(city.getId());
    cr.setName(city.getName());
    Countrys country = city.getCountry();
    if (country != null) {
      cr.setCountry(country);
    }
    return cr;
  }

}
