package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.BookingDao;
import com.softserve.tourcomp.dao.impl.mapper.BookingMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.entity.Bookings;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCBookingDao extends JDBCGenericDao<Bookings> implements BookingDao {
  private final String findBookingsByUserEmailQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON USERS.Id = BOOKINGS.id_user WHERE USERS.email = ?";
  private final String findBookingsByUserIdQuery = "SELECT * FROM BOOKINGS WHERE id_user = ?";
  private final String findBookingsByUserIdAndDatesQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON USERS.Id = BOOKINGS.id_user WHERE USERS.id = ?";
  private final String findBookingsByHotelId = "";
  private final String findBookingsByHotelIdAndDateS = "";
  private final String findBookingsByCityId = "";
  private final String findBookingsByCityAndDates = "";
  private final String findBookingsByCountryIdQuery = "";
  private final String findBookingsByCountryIdAndDatesFromQuery = "";
  private JDBCHotelDao jdbcHotelDao = new JDBCHotelDao(connection);
  private JDBCUserDao jdbcUserDao = new JDBCUserDao(connection);

  public JDBCBookingDao(Connection connection) {
    super(connection, "INSERT INTO BOOKINGS(startDate, endDate, amountRooms, price, id_user, id_hotel) VALUES(?, ?, ?, ?, ?, ?)",
            "SELECT * FROM BOOKINGS WHERE id = ?",
            "SELECT * FROM BOOKINGS ",
            "SELECT COUNT(*) FROM BOOKINGS",
            "COUNT(*)",
            "UPDATE BOOKINGS SET startDate = ?, endDate = ?, amountRooms = ?, price = ?, id_user = ?, id_hotel = ? WHERE id = ?",
            7,
            "DELETE FROM BOOKINGS WHERE id = ?",
            new BookingMapper());
  }

  public static void main(String[] args) {
    JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
    JDBCBookingDao jdbcBookingDao = jdbcDaoFactory.createBookingDao();
    Bookings book = jdbcBookingDao.findById(6L).get();
/*    System.out.println("Before :"+ book);
book.setHotel(jdbcDaoFactory.createHotelDao().findById(17L).get());
book.setUser(jdbcDaoFactory.createUserDao().findById(14L).get());
jdbcBookingDao.update(book);
book=jdbcBookingDao.findById(6L).get();
    System.out.println("After :"+ book);
*/
   /*for (Bookings bookker:
    jdbcBookingDao.findBookingsByUserId(7L)
) {
      System.out.println(bookker);
    }
*/

  }

  /**
   * @param email
   * @return
   */
  @Override
  public List<Bookings> findByUserEmail(String email) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByUserEmailQuery)) {
      statement.setString(1, email);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  /**
   * @param statement
   * @return
   * @throws SQLException
   */
  @Override
  public List<Bookings> getAllFromStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Bookings> bookingMapper = new BookingMapper();
    List<Bookings> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      Bookings book = extractEntity(rs);
      book.setHotel(jdbcHotelDao.findHotelByBookingId(book.getId()).get());
      book.setUser(jdbcUserDao.findUserByBookingId(book.getId()).get());
      entities.add(book);
    }
    return entities;
  }

  /**
   * @param rs
   * @return
   * @throws SQLException
   */
  @Override
  public Bookings extractEntity(ResultSet rs) throws SQLException {
    Bookings book = mapper.extractFromResultSet(rs);
    book.setHotel(jdbcHotelDao.findHotelByBookingId(book.getId()).get());
    book.setUser(jdbcUserDao.findUserByBookingId(book.getId()).get());
    return book;
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public List<Bookings> findBookingsByUserId(Long userId) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByUserIdQuery)) {
      statement.setLong(1, userId);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByDates(LocalDate fromDate, LocalDate endDate) {
    return null;
  }

  @Override
  public List<Bookings> findBookingsByHotelId(Long hotelId) {
    return null;
  }

  /**
   * @param entity
   * @return
   */
  @Override
  long getId(Bookings entity) {
    return entity.getId();
  }

  /**
   * @param entity
   * @param Id
   * @throws SQLException
   */
  @Override
  void setId(Bookings entity, long Id) throws SQLException {
    entity.setId(Id);
  }

  /**
   * @param statement
   * @param entity
   * @throws SQLException
   */
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
  public List<Bookings> findBookingsByHotelId(Long hotelId, LocalDate fromDate, LocalDate endDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByHotelIdAndDateS)) {
      statement.setLong(1, hotelId);
      statement.setDate(2, Date.valueOf(fromDate));
      statement.setDate(3, Date.valueOf(endDate));
      found = getAllFromStatement(statement);
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
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Bookings> findBookingsByCityId(Long cityId, LocalDate fromDate, LocalDate endDate) {
    return null;
  }

  @Override
  public List<Bookings> findBookingsByCountryId(Long countryId) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByCountryIdQuery)) {
      statement.setLong(1, countryId);
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public int countBookedRooms(Long hotelId, LocalDate fromDate, LocalDate endDate) {
    return 0;
  }

  @Override
  public List<Bookings> findBookingsByCountryId(Long countryId, LocalDate fromDate, LocalDate endDate) {
    List<Bookings> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findBookingsByCountryIdAndDatesFromQuery)) {
      statement.setLong(1, countryId);
      statement.setDate(2, Date.valueOf(fromDate));
      statement.setDate(3, Date.valueOf(endDate));
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }


}
