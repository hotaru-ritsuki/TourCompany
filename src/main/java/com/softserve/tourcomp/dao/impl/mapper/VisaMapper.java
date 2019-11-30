package com.softserve.tourcomp.dao.impl.mapper;

import com.softserve.tourcomp.entity.Visas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisaMapper implements ObjectMapper<Visas> {
  @Override
  public Visas extractFromResultSet(ResultSet rs) throws SQLException {
    Visas visa=new Visas();
    CountryMapper country=new CountryMapper();
    visa.setId(rs.getLong("VISAS.id"));
    visa.setName(rs.getString("VISAS.name"));
    visa.setCountry(country.extractFromResultSet(rs));
    //visa UsERS MANY TO MANY
    return visa;
  }
}
