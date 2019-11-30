package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Hotels;

import java.util.List;
import java.util.Optional;

public interface HotelDao extends GenericDao<Hotels>{

  List<Hotels> findHotelsByCityId(Long cityId);

  Optional<Hotels> findHotelByName(String nameHotel);

  List<Hotels> findHotelsByLowerPricePerNight(Integer pricePerNight);

  List<Hotels> findHotelsByHigherPricePerNight(Integer pricePerNight);

  List<Hotels> findHotelsByPricesPerNight(Integer lowerPricePerNight,Integer higherPricePerNight);

  List<Hotels> findHotelsByCountryId(Long countryId);

  List<Hotels> findHotelsByNumberOfRooms(Integer numberOfRooms);

}
