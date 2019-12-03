package com.softserve.tourcomp.dao.impl.mapper;

import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements ObjectMapper<Citys> {

  @Override
  public Citys extractFromResultSet(ResultSet rs) throws SQLException {
    Citys city=new Citys();
    CountryMapper country=new CountryMapper();
    city.setId(rs.getLong("CITYS.id"));
    city.setName(rs.getString("CITYS.name"));
    try {
      city.setCountry(this.extractCountry(rs));
    }
    catch (SQLException exc){
      city.setCountry(new Countrys());
    }
    return city;
  }
  public Countrys extractCountry(ResultSet rs) throws SQLException{
    CountryMapper countryMap=new CountryMapper();
    Countrys country = countryMap.extractFromResultSet(rs);
    return country;
  }
}
