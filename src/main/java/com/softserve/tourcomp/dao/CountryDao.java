package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;

import java.util.List;
import java.util.Optional;

public interface CountryDao extends GenericDao<Countrys>{

  Optional<Countrys> findByCountryName(String nameCountry);

  Optional<Countrys> findCountryByCityId(Long cityId);

}