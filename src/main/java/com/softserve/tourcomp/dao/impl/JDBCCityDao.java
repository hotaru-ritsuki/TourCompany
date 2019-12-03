package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.CityDao;
import com.softserve.tourcomp.dao.impl.mapper.CityMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.dao.impl.mapper.UserMapper;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCityDao extends JDBCGenericDao<Citys> implements CityDao {
  private final String FindByCityNameQuery = "SELECT * FROM CITYS WHERE name = ?";
  private final String findCityByCountryIdQuery = "SELECT * FROM CITYS WHERE id_country = ?";
  private final String FindByHotelIdQuery = "SELECT * FROM CITYS LEFT JOIN BOOKINGS ON CITYS.id = HOTELS.id_city WHERE HOTELS.id = ?";

  public JDBCCityDao(Connection connection) {
    super(connection,
          "INSERT INTO CITYS(name, id_country) VALUES(?,?)",
          "SELECT * FROM CITYS LEFT JOIN COUNTRYS ON id_country = COUNTRYS.id WHERE id = ?",
          "SELECT SQL_CALC_FOUND_ROWS * FROM CITYS LIMIT ?,?",
          "SELECT * FROM CITYS LEFT JOIN COUNTRYS ON id_country = COUNTRYS.id ",
          "SELECT COUNT(*) FROM CITYS ",
          "COUNT(*)",
          "UPDATE CITYS SET name = ?, id_country = ? WHERE id = ?",
          3,
          "DELETE FROM CITYS WHERE id = ?",
          new CityMapper());
  }

  @Override
  long getId(Citys entity) {
    return entity.getId();
  }

  @Override
  void setId(Citys entity, long Id) throws SQLException {
    entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Citys entity) throws SQLException {
    statement.setString(1, entity.getName());
    statement.setLong(2, entity.getCountry().getId());
  }

  @Override
  public Optional<Citys> findByCityName(String nameCity) {
    Citys entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByCityNameQuery)) {
      statement.setString(1, nameCity);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        entity = extractEntity(result);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Optional.ofNullable(entity);
  }

  @Override
  public List<Citys> findCityByCountryId(Long countryId) {
    List<Citys> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findCityByCountryIdQuery)) {
      statement.setLong(1, countryId);
      found = getAllFromCitysStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public Optional<Citys> findCityByHotelId(Long hotelId) {
    Citys entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByHotelIdQuery)) {
      statement.setLong(1, hotelId);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        entity = extractEntity(result);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Optional.ofNullable(entity);
  }

  private List<Citys> getAllFromCitysStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Citys> cityMapper = new CityMapper();
    List<Citys> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(cityMapper.extractFromResultSet(rs));
    }
    return entities;
  }

}
