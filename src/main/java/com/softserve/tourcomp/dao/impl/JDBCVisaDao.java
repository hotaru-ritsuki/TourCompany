package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.dao.impl.mapper.UserMapper;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCVisaDao extends JDBCGenericDao<Visas> implements VisaDao {
  private final String createUsersToVisaQuery = "INSERT INTO USERS_VISAS (id_user,id_visa) VALUES (?,?)";
  private final String deleteUsersToVisaQuery = "DELETE FROM USERS_VISAS WHERE id_visa = ?";
  private final String selectUsersToVisaQuery = "SELECT * FROM USERS_VISAS JOIN USERS ON VISAS.id_user=USERS.id WHERE id_visa = ?";
  private final String findVisasByCountryIdQuery = "";


  public JDBCVisaDao(Connection connection) {
    super(connection, "INSERT INTO VISAS (name) VALUES (?)",
            "SELECT * FROM VISAS WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM VISAS LIMIT ?,?",
            "SELECT * FROM VISAS",
            "SELECT COUNT(*) FROM VISAS",
            "COUNT(*)",
            "UPDATE VISAS SET name = ? WHERE id= ?",
            2,
            "DELETE FROM VISA WHERE id = ?",
            new VisaMapper());
  }

  @Override
  long getId(Visas entity) {
    return entity.getId();
  }

  @Override
  void setId(Visas entity, long Id) throws SQLException {
    entity.setId(Id);
  }

  @Override
  void setEntityValues(PreparedStatement statement, Visas entity) throws SQLException {
    statement.setString(1, entity.getName());
  }

  @Override
  public boolean create(Visas entity) {
    boolean created = false;
    try (PreparedStatement statement = connection.prepareStatement(CreateQuery, Statement.RETURN_GENERATED_KEYS)) {

      int affected = insertIntoDb(statement, entity);
      if (affected == 1) {
        setId(entity, getId(entity, statement));
        created = true;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return created;
  }

  @Override
  boolean createAction(PreparedStatement statement,Visas entity) throws SQLException{
    if (insertIntoDb(statement,entity) == 1) {
      setId(entity, getId(entity, statement));
      //insertUsersFromVisa(entity);
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(long visaId) {
    boolean affected = false;
    try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
      affected = transaction(statement, visaId, this::deleteVisa);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return affected;
  }

  private boolean deleteVisa(PreparedStatement statement, Long id) throws SQLException {
    statement.setLong(1, id);
    //deleteUsersFromVisa(id);
    return statement.executeUpdate() > 0;
  }

  @Override
  int updateOnDb(PreparedStatement statement, Visas entity) throws SQLException {
    setEntityValues(statement, entity);
    statement.setLong(UpdateIdParameterIndex, entity.getId());
    //deleteUsersFromVisa(entity.getId());
    //insertUsersFromVisa(entity);
    return statement.executeUpdate();
  }

  @Override
  Visas extractEntity(ResultSet rs) throws SQLException {
    Visas extracted = mapper.extractFromResultSet(rs);
    return extracted;
  }

//  private void insertUsersFromVisa(Visas from) throws SQLException {
//    try (PreparedStatement insertUsers = connection.prepareStatement(createUsersToVisaQuery)) {
//      insertUsers.setLong(1, from.getId());
//    }
//  }
//
//  private void deleteUsersFromVisa(long visaId) throws SQLException {
//    try (PreparedStatement statement = connection.prepareStatement(deleteUsersToVisaQuery)) {
//      statement.setLong(1, visaId);
//      statement.execute();
//    }
//  }
//
//  private List<Users> getUsersFromVisa(long visaId) throws SQLException {
//    List<Users> result = new ArrayList<>();
//    try (PreparedStatement statement = connection.prepareStatement(selectUsersToVisaQuery)) {
//      statement.setLong(1, visaId);
//      ObjectMapper<Users> userMapper = new UserMapper();
//      ResultSet rs = statement.executeQuery();
//      while (rs.next()) {
//        result.add(userMapper.extractFromResultSet(rs));
//      }
//    }
//    return result;
//  }

  @Override
  public Optional<Visas> findById(Long visaId) {
    Visas entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindByIDQuery)) {
      statement.setLong(1, visaId);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        entity = extractEntity(result);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Optional.ofNullable(entity);
  }

  private List<Visas> getAllFromVisasStatement(PreparedStatement statement) throws SQLException {
    List<Visas> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      Visas visa=mapper.extractFromResultSet(rs);
      /*
      Visas visa = extractEntity(rs);
       */
      entities.add(visa);
    }
    return entities;
  }

}
