package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.mapper.CountryMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCountryDao extends JDBCGenericDao<Countrys> implements CountryDao {
  private final String FindByCountryNameQuery = "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON id_visa = COUNTRYS.id WHERE name = ?";
  private final String FindByCityIdQuery = "SELECT * FROM COUNTRYS LEFT JOIN CITYS ON COUNTRYS.id = CITYS.id_country WHERE CITYS.id = ?";
  private final String findCountrysByVisaIdQuery = "SELECT * FROM COUNTRYS WHERE id_visa = ?";

  public JDBCCountryDao(Connection connection) {
    super(connection,"INSERT INTO COUNTRYS (name, id_visa) VALUES (?, ?)",
            "SELECT * FROM COUNTRYS WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM COUNTRYS LIMIT ?,?",
            "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON id_visa = VISAS.id",
            "SELECT COUNT(*) FROM COUNTRYS",
            "COUNT(*)",
            "UPDATE COUNTRYS SET name = ?, id_visa = ? WHERE id = ?",
            3,
            "DELETE FROM COUNTRYS WHERE id = ?",
            new CountryMapper());
  }

  @Override
  long getId(Countrys entity) {
    return entity.getId();
  }

  @Override
  void setId(Countrys entity, long Id) throws SQLException {
entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Countrys entity) throws SQLException {
statement.setString(1,entity.getName());
statement.setLong(2,entity.getVisa().getId());
  }

  @Override
  public Optional<Countrys> findByCountryName(String nameCountry) {
    Countrys entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByCountryNameQuery)) {
      statement.setString(1, nameCountry);
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
  public Optional<Countrys> findCountryByCityId(Long cityId) {
    Countrys entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByCityIdQuery)) {
      statement.setLong(1, cityId);
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
  public List<Countrys> findCountriesByVisaId(Long visaId) {
    List<Countrys> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findCountrysByVisaIdQuery)) {
      statement.setLong(1, visaId);
      found = getAllFromCountrysStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  private List<Countrys> getAllFromCountrysStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Countrys> countryMapper = new CountryMapper();
    List<Countrys> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(countryMapper.extractFromResultSet(rs));
    }
    return entities;
  }

}
