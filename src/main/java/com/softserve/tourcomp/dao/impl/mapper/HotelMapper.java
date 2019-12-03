package com.softserve.tourcomp.dao.impl.mapper;

import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Hotels;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelMapper implements ObjectMapper<Hotels> {

  @Override
  public Hotels extractFromResultSet(ResultSet rs) throws SQLException {
    Hotels hotel = new Hotels();
    CityMapper city = new CityMapper();
    Citys cit = city.extractFromResultSet(rs);
    hotel.setCity(cit);
    hotel.setCountry(cit.getCountry());
    hotel.setId(rs.getLong("HOTELS.id"));
    hotel.setDiscription(rs.getString("HOTELS.description"));
    hotel.setName(rs.getString("HOTELS.name"));
    hotel.setNumberRoom(rs.getInt("HOTELS.numberRoom"));
    hotel.setPriceNight(rs.getInt("HOTELS.priceNight"));
    return hotel;
  }
}
