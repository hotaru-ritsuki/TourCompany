package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.BookingDao;
import com.softserve.tourcomp.dao.impl.mapper.BookingMapper;
import com.softserve.tourcomp.dao.impl.mapper.HotelMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.entity.Bookings;
import com.softserve.tourcomp.entity.Hotels;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCBookingDao extends JDBCGenericDao<Bookings> implements BookingDao {
  private final String findByUserEmailQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON USERS.Id = BOOKINGS.id_user WHERE USERS.email = ?";
  private final String findByUserIdQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON USERS.Id = BOOKINGS.id_user WHERE USERS.id = ?";
  private final String findByHotelIdAndDateQuery = "";
  private final String findByHotelIdAndDateS = "";
  private final String findBookingsByCityId="";
  private final String findBookingsByCityAndDateId = "";
  private final String findByCountryIdQuery="";
  private final String findByCountryIdAndDateFromQuery="";
  private final String findByCountryIdAndDatesFromQuery="";


  public JDBCBookingDao(Connection connection) {
    super(connection, "INSERT INTO BOOKINGS(startDate, endDate, amountRooms, price, id_user, id_hotel) VALUES(?, ?, ?, ?, ?, ?)",
            "SELECT * FROM BOOKING WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM BOOKINGS LIMIT ?,?",
            "SELECT * FROM BOOKINGS",
            "SELECT COUNT(*) FROM BOOKINGS",
            "COUNT(*)",
            "UPDATE BOOKINGS SET startDate = ?, endDate = ?, amountRooms = ?, price = ?, id_user = ?, id_hotel = ? WHERE id = ?",
            7,
            "DELETE FROM BOOKINGS WHERE id = ?",
            new BookingMapper());
  }


  @Override
  public List<Bookings> findByUserEmail(String email) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByUserEmailQuery)) {
      statement.setString(1, email);
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  private List<Bookings> getAllFromBookingsStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Bookings> bookingMapper = new BookingMapper();
    List<Bookings> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(bookingMapper.extractFromResultSet(rs));
    }
    return entities;
  }


  @Override
  public List<Bookings> findBookingsByUserId(Long userId) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByUserIdQuery)) {
      statement.setLong(1, userId);
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  long getId(Bookings entity) {
    return entity.getId();
  }

  @Override
  void setId(Bookings entity, long Id) throws SQLException {
    entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Bookings entity) throws SQLException {
    statement.setDate(1, Date.valueOf(entity.getStartDate()));
    statement.setDate(2, Date.valueOf(entity.getEndDate()));
    statement.setInt(3, entity.getAmountRooms());
    statement.setInt(4, entity.getPrice());
    statement.setLong(5, entity.getUser().getId());
    statement.setLong(6, entity.getHotel().getId());
  }

  @Override
  public List<Bookings> findBookingsByHotelIdAndDate(Long hotelId, LocalDate fromDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByHotelIdAndDateQuery)) {
      statement.setLong(1, hotelId);
      statement.setDate(2, Date.valueOf(fromDate));
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByHotelIdAndDate(Long hotelId, LocalDate fromDate, LocalDate endDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByHotelIdAndDateS)) {
      statement.setLong(1, hotelId);
      statement.setDate(2,Date.valueOf(fromDate));
      statement.setDate(3,Date.valueOf(endDate));
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByCityId(Long hotelId) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByCityId)) {
      statement.setLong(1, hotelId);
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  public List<Bookings> findBookingsByCityId(Long hotelId, LocalDate fromDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByCityAndDateId)) {
      statement.setLong(1, hotelId);
      statement.setDate(2,Date.valueOf(fromDate));
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByCountryId(Long countryId) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByCountryIdQuery)) {
      statement.setLong(1,countryId );
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByCountryId(Long countryId, LocalDate fromDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByCountryIdAndDateFromQuery)) {
      statement.setLong(1, countryId);
      statement.setDate(2,Date.valueOf(fromDate));
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByCountryId(Long countryId, LocalDate fromDate, LocalDate endDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findByCountryIdAndDatesFromQuery)) {
      statement.setLong(1, countryId);
      statement.setDate(2,Date.valueOf(fromDate));
      statement.setDate(3,Date.valueOf(endDate));
      found = getAllFromBookingsStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }


}
