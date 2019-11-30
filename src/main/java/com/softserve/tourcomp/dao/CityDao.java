package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Citys;

import java.util.List;
import java.util.Optional;

public interface CityDao extends GenericDao<Citys> {

  Optional<Citys> findByCityName(String nameCity);

  List<Citys> findCityByCountryId(Long countryId);

  Optional<Citys> findCityByHotelId(Long hotelId);

}
