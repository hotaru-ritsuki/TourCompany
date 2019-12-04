package com.softserve.tourcomp.service.inteface;

import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.country.CountryResponse;
import com.softserve.tourcomp.entity.Countrys;

import java.util.List;

public interface CountryServiceInf {
  List<CountryResponse> findAll();

  boolean update(Long id, CountryRequest country);

  boolean create(CountryRequest country);

  Countrys findOneCountry(Long id);

  CountryResponse findOne(Long id);
}
