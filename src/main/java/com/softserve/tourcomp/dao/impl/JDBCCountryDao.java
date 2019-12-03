package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.mapper.CountryMapper;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Countrys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCountryDao extends JDBCGenericDao<Countrys> implements CountryDao {
  private final String FindByCountryNameQuery = "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON id_visa = VISAS.id WHERE COUNTRYS.name = ?";
  private final String FindByCityIdQuery = "SELECT * FROM COUNTRYS LEFT JOIN CITYS ON COUNTRYS.id = CITYS.id_country LEFT JOIN VISAS ON id_visa = VISAS.id WHERE CITYS.id = ?";
  private final String findCountrysByVisaIdQuery = "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON countrys.id_visa = VISAS.id WHERE id_visa = ?";
  private final String deleteAllCitiesRelatedCountry = "DELETE FROM CITYS WHERE id_country = ?";

  public JDBCCountryDao(Connection connection) {
    super(connection, "INSERT INTO COUNTRYS (name, id_visa) VALUES (?, ?)",
            "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON COUNTRYS.id_visa = VISAS.id WHERE COUNTRYS.id = ?",
            "SELECT * FROM COUNTRYS LEFT JOIN VISAS ON id_visa = VISAS.id",
            "SELECT COUNT(*) FROM COUNTRYS",
            "COUNT(*)",
            "UPDATE COUNTRYS SET name = ?, id_visa = ? WHERE id = ?",
            3,
            "DELETE FROM COUNTRYS WHERE id = ?",
            new CountryMapper());
  }

  public static void main(String[] args) {
    JDBCCountryDao jdbcCountryDao = new JDBCDaoFactory().createCountryDao();
    //jdbcCountryDao.create(new Countrys(5L,"Ukraine",new Visas(1L,"sdsf")));
    //System.out.println(jdbcCountryDao.findById(6).get());
    /*for (Countrys country:jdbcCountryDao.findAll()) {
      System.out.println(country);
    }
     */
    //System.out.println(jdbcCountryDao.count());
    /*jdbcCountryDao.update(new Countrys(3L,"Poland",new Visas(2L,"updated")));
    System.out.println(jdbcCountryDao.findById(3L).get());
  */
//jdbcCountryDao.delete(6L);
    //System.out.println(jdbcCountryDao.findByCountryName("Ukraine").get());
    System.out.println(jdbcCountryDao.findCountryByCityId(2L));
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
  public Countrys extractEntity(ResultSet rs) throws SQLException {
    VisaMapper visaMapper = new VisaMapper();
    Countrys country = mapper.extractFromResultSet(rs);
    country.setVisa(visaMapper.extractFromResultSet(rs));
    return country;
  }

  @Override
  void setEntityValues(PreparedStatement statement, Countrys entity) throws SQLException {
    statement.setString(1, entity.getName());
    statement.setLong(2, entity.getVisa().getId());
  }

  @Override
  public boolean delete(long countryId) {
    boolean affected = false;
    JDBCVisaDao jdbcVisaDao = new JDBCDaoFactory().createVisaDao();
    try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
      statement.setLong(1, countryId);
      deleteAllCitiesRelatedCountry(countryId);
      affected = deleteAllCitiesRelatedCountry(countryId) && transaction(statement, this::deleteEntity);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return affected;
  }

  private boolean deleteAllCitiesRelatedCountry(long countryId) {
    boolean affected = false;
    try (PreparedStatement statement = connection.prepareStatement(deleteAllCitiesRelatedCountry)) {
      statement.setLong(1, countryId);
      affected = transaction(statement, this::deleteEntity);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return affected;
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
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Countrys> getAllFromStatement(PreparedStatement statement) throws SQLException {
    List<Countrys> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(extractEntity(rs));
    }
    return entities;
  }

}
