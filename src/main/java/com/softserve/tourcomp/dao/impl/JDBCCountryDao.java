package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.mapper.CountryMapper;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCCountryDao extends JDBCGenericDao<Countrys> implements CountryDao {
  private final String FindByCountryNameQuery = "SELECT * FROM COUNTRYS WHERE name = ?";
  private final String FindByCityIdQuery = "SELECT * FROM COUNTRYS LEFT JOIN CITYS ON COUNTRYS.id = CITYS.id_country WHERE CITYS.id = ?";

  public JDBCCountryDao(Connection connection) {
    super(connection,"INSERT INTO COUNTRYS (name) VALUES (?)",
            "SELECT * FROM COUNTRYS WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM COUNTRYS LIMIT ?,?",
            "SELECT * FROM COUNTRYS",
            "SELECT COUNT(*) FROM COUNTRYS",
            "COUNT(*)",
            "UPDATE COUNTRYS name = ? WHERE id = ?",
            2,
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

}
