package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.DaoFactory;
import com.softserve.tourcomp.dao.HotelDao;
import com.softserve.tourcomp.dao.impl.mapper.CityMapper;
import com.softserve.tourcomp.dao.impl.mapper.HotelMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Hotels;
import com.softserve.tourcomp.entity.Visas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCHotelDao extends JDBCGenericDao<Hotels> implements HotelDao {
  private final String findHotelsByCityIdQuery = "SELECT * FROM HOTELS id_city = ?";
  private final String findHotelsByCountryIdQuery = "SELECT * FROM HOTELS LEFT JOIN CITYS ON hotels.id_city = citys.id WHERE CITYS.id_country = ?";
  private final String findHotelsByPricePerNightQuery = "SELECT * FROM HOTELS WHERE priceNight > ? AND priceNight < ?";
  private final String findHotelsByLowerPricePerNightQuery = "SELECT * FROM HOTELS WHERE priceNight < ? ";
  private final String findHotelsByHigherPricePerNightQuery = "SELECT * FROM HOTELS WHERE priceNight > ?";
  private final String findHotelsByNumberOfRoomsQuery = "SELECT * FROM HOTELS WHERE numberRooms > ?";
  private final String FindByHotelNameQuery = "SELECT * FROM HOTELS WHERE name = ?";
private JDBCCityDao jdbcCityDao=new JDBCCityDao(connection);
  public JDBCHotelDao(Connection connection) {
    super(connection,"INSERT INTO HOTELS (name, numberRooms, id_city, priceNight, discription) VALUES (?,?,?,?,?)",
            "SELECT * FROM HOTELS WHERE id = ?",
            "SELECT * FROM HOTELS",
            "SELECT COUNT(*) FROM HOTELS",
            "COUNT(*)",
            "UPDATE HOTELS SET name = ?, numberRooms = ?,id_city = ?, priceNight = ?, discription = ? WHERE id = ?",
            6,
            "DELETE FROM HOTELS WHERE id = ?",
            new HotelMapper());
  }

  @Override
  long getId(Hotels entity) {
    return entity.getId();
  }

  @Override
  void setId(Hotels entity, long Id) throws SQLException {
entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Hotels entity) throws SQLException {
statement.setString(1,entity.getName());
statement.setInt(2,entity.getNumberRoom());
statement.setLong(3,entity.getCity().getId());
statement.setInt(4,entity.getPriceNight());
statement.setString(5,entity.getDiscription());
  }

  @Override
  public List<Hotels> findHotelsByCityId(Long cityId) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByCityIdQuery)) {
      statement.setLong(1, cityId);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public Optional<Hotels> findHotelByName(String nameHotel) {
    Hotels entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByHotelNameQuery)) {
      statement.setString(1, nameHotel);
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
  public List<Hotels> findHotelsByLowerPricePerNight(Integer pricePerNight) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByLowerPricePerNightQuery)) {
      statement.setInt(1, pricePerNight);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Hotels> findHotelsByHigherPricePerNight(Integer pricePerNight) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByHigherPricePerNightQuery)) {
      statement.setInt(1, pricePerNight);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

@Override
  public List<Hotels> findHotelsByPricesPerNight(Integer lowerPricePerNight,Integer higherPricePerNight) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByPricePerNightQuery)) {
      statement.setInt(1, lowerPricePerNight);
      statement.setInt(2, higherPricePerNight);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }


  public List<Hotels> findHotelsByCountryId(Long countryId) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByCountryIdQuery)) {
      statement.setLong(1, countryId);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Hotels> findHotelsByNumberOfRooms(Integer numberOfRooms) {
    List<Hotels> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findHotelsByNumberOfRoomsQuery)) {
      statement.setInt(1, numberOfRooms-1);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }
@Override
  public List<Hotels> getAllFromStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Hotels> hotelMapper = new HotelMapper();
    List<Hotels> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      Hotels hotel = hotelMapper.extractFromResultSet(rs);
      hotel.setCity(jdbcCityDao.findCityByHotelId(hotel.getId()).get());
      entities.add(hotel);
    }
    return entities;
  }
  Hotels extractEntity(ResultSet rs) throws SQLException {
     Hotels hotel = mapper.extractFromResultSet(rs);
  hotel.setCity(jdbcCityDao.findCityByHotelId(hotel.getId()).get());
  return hotel;
  }
}
