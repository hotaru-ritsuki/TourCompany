package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.UserDao;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.dao.impl.mapper.UserMapper;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCGenericDao<Users> implements UserDao {
  private final String FindUserByEmailQuery = "SELECT * FROM USERS WHERE email = ?";
  private final String FindUserByIdQuery= "SELECT * FROM USERS WHERE id = ?";
  private final String FindUserByUsernameQuery = "SELECT * FROM USERS WHERE firstName = ? AND lastName = ?";
  private final String findUsersByCountryIdQuery = "SELECT * FROM USERS WHERE country_id = ?";
  private final String findUsersByVisaIdQuery = "SELECT * FROM USERS_VISAS LEFT JOIN USERS ON USERS.id = USERS_VISAS.id_user WHERE id_visa = ?";
  private final String FindUserByBookingIdQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON BOOKINGS.id_user = USERS.id WHERE BOOKINGS.id = ?";

  private final String createVisasQuery = "INSERT INTO USERS_VISAS(id_user, id_visa) VALUES (?,?)";
  private final String deleteVisasQuery = "DELETE FROM USERS_VISAS WHERE id_visa = ?";
  private final String FindVisasQuery = "SELECT ";

  public JDBCUserDao(Connection connection) {
    super(connection,"INSERT INTO USERS (firstName, lastName, email, password, isAdmin, id_country) VALUES (?, ?, ?, ?, ?, ?);",
            "SELECT * FROM USERS LEFT JOIN COUNTRYS ON id_country = COUNTRYS.id WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM USERS LIMIT ?,?",
            "SELECT * FROM USERS LEFT JOIN COUNTRYS ON id_country = COUNTRYS.id",
            "SELECT COUNT(*) FROM USERS LEFT JOIN COUNTRYS ON id_country = COUNTRYS.id",
            "COUNT(*)",
            "UPDATE USERS SET firstName = ?, lastName = ?, email = ?, password = ?, isAdmin = ?, id_country = ? WHERE id = ?",
            7,
            "DELETE FROM USERS WHERE id = ?",
            new UserMapper());
  }

  @Override
  public Optional<Users> findUserByEmail(String email) {
    Users entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindUserByEmailQuery)) {
      statement.setString(1, email);
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
  public Optional<Users> findById(Long usrId) {
    Users entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindUserByIdQuery)) {
      statement.setLong(1, usrId);
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
  public Optional<Users> findUserByName(String firstName, String lastName) {
    Users entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindUserByUsernameQuery)) {
      statement.setString(1, firstName);
      statement.setString(2, lastName);
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
  public List<Users> findUsersByCountryId(Long countryId) {
    List<Users> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findUsersByCountryIdQuery)) {
      statement.setLong(1, countryId);
      found = getAllFromUsersStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }
  
  private List<Users> getAllFromUsersStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Users> userMapper = new UserMapper();
    List<Users> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(userMapper.extractFromResultSet(rs));
    }
    return entities;
  }

  @Override
  public Optional<Users> findUserByBookingId(Long bookingId) {
    Users entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindUserByBookingIdQuery)) {
      statement.setLong(1, bookingId);
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
  public List<Users> findUserByVisaId(Long visaId) {
    List<Users> found = null;

    try (PreparedStatement statement = connection.prepareStatement(findUsersByVisaIdQuery)) {
      statement.setLong(1, visaId);
      found = getAllFromUsersStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  long getId(Users entity) {
    return entity.getId();
  }

  @Override
  void setId(Users entity, long Id) throws SQLException {
entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Users entity) throws SQLException {
statement.setString(1,entity.getFirstName());
    statement.setString(2,entity.getLastName());
    statement.setString(3,entity.getEmail());
    statement.setString(4,entity.getPassword());
    statement.setBoolean(5,entity.getIsAdmin());
    statement.setLong(6,entity.getCountry().getId());
  }

  @Override
  Users extractEntity(ResultSet rs) throws SQLException {
    Users extracted = mapper.extractFromResultSet(rs);
    extracted.setVisas(getVisas(extracted.getId()));
    return extracted;
  }


  @Override
  public boolean create(Users entity) {
    boolean created = false;
    try (PreparedStatement statement = connection.prepareStatement(CreateQuery, Statement.RETURN_GENERATED_KEYS)) {

      int affected = insertIntoDb(statement,entity);
      if (affected == 1) {
        setId(entity, getId(entity, statement));
        insertVisas(entity);
        created = true;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return created;
  }

  @Override
  public boolean delete(long id) {
    boolean affected = false;
    try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
      affected = transaction(statement,id,this::deleteUsers);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return affected;
  }

  boolean deleteUsers(PreparedStatement statement, Long id) throws SQLException {
    statement.setLong(1, id);
    deleteVisas(id);
    return statement.executeUpdate() > 0;
  }

  private void insertVisas(Users from) throws SQLException {
    try (PreparedStatement insertVisas = connection.prepareStatement(createVisasQuery)){
      insertVisas.setLong(1,from.getId());
      for(Visas visa: from.getVisas()) {
        insertVisas.setLong(2, visa.getId());
        insertVisas.executeUpdate();
      }
    }
  }

  private void deleteVisas(long userId) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(deleteVisasQuery)) {
      statement.setLong(1, userId);
      statement.execute();
    }
  }

  private List<Visas> getVisas(long userId) throws SQLException {
    List<Visas> result = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(FindVisasQuery)) {
      statement.setLong(1,userId);
      ResultSet rs =  statement.executeQuery();
      VisaMapper visaMapper = new VisaMapper();
      while (rs.next()) {
        result.add(visaMapper.extractFromResultSet(rs));
      }
    }
    return result;
  }
}
