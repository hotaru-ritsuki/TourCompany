package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Visas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCVisaDao extends JDBCGenericDao<Visas> implements VisaDao {
  private final String deleteUsersToVisaQuery = "DELETE FROM USERS_VISAS WHERE id_visa = ?";
  private final  String deleteCountrysToVisaQuery = "DELETE FROM COUNTRYS WHERE id_visa = ?";
  private final String selectUsersToVisaQuery = "SELECT * FROM USERS_VISAS JOIN USERS ON VISAS.id_user=USERS.id WHERE id_visa = ?";
  private final String findVisasByCountryIdQuery = "SELECT * FROM VISAS JOIN COUNTRYS ON id_visa = VISAS.id";


  public JDBCVisaDao(Connection connection) {
    super(connection, "INSERT INTO VISAS (name) VALUES (?)",
            "SELECT * FROM VISAS WHERE id = ?",
            "SELECT * FROM VISAS",
            "SELECT COUNT(*) FROM VISAS",
            "COUNT(*)",
            "UPDATE VISAS SET name = ? WHERE id= ?",
            2,
            "DELETE FROM VISAS WHERE id = ?",
            new VisaMapper());
  }

  public static void main(String[] args) {
    JDBCDaoFactory jdbcDaoFactory=new JDBCDaoFactory();
    JDBCVisaDao jdbcVisaDao=jdbcDaoFactory.createVisaDao();
    /*Visas visa =new Visas(12L,"wwwwwwolol");
    System.out.println(jdbcVisaDao.create(visa));
    System.out.println(visa.getId());
    */
    /*
    //System.out.println(jdbcVisaDao.findById(5L).get());
     */
    /*for (Visas visa : jdbcVisaDao.findAll()) {
      System.out.println(visa);
    }
    */
    //System.out.println(jdbcVisaDao.count());
    /*System.out.println(jdbcVisaDao.update(new Visas(2L, "updated"))); true
    System.out.println(jdbcVisaDao.update(new Visas(15L, "insert"))); false
     */
   // System.out.println(jdbcVisaDao.delete(12L)); //CONSTRASINTS COUNTRY,CITYS



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

  /**
   *
   * @param entity
   * @return
   */
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

  /**
   * Implements transactions, if deleting from USERS_VISAS wasn't successful,it returns false;
   *
   * @param visaId Visa Id which need to delete
   * @return
   */
  @Override
  public boolean delete(long visaId) {
    boolean affected = false;
    try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
      deleteUsersRelatedVisa(visaId);
      affected = transaction(statement, visaId, this::deleteVisa);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return affected;
  }

  /**
   * Supporting method for delete(long visaId) to develop transactions
   *
   * @param statement PreparedStatement with DeleteQuery
   * @param id Visa Id which need to delete from VISAS table
   * @return Returns true if method was completed, false if not
   * @throws SQLException
   */
  private boolean deleteVisa(PreparedStatement statement, Long id) throws SQLException {
    statement.setLong(1, id);
    //deleteUsersFromVisa(id);
    return statement.executeUpdate() > 0;
  }


  @Override
  int updateOnDb(PreparedStatement statement, Visas entity) throws SQLException {
    setEntityValues(statement, entity);
    statement.setLong(UpdateIdParameterIndex, entity.getId());
    return statement.executeUpdate();
  }

  @Override
  Visas extractEntity(ResultSet rs) throws SQLException {
    return mapper.extractFromResultSet(rs);
  }

  private boolean deleteUsersRelatedVisa(long visaId) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(deleteUsersToVisaQuery)) {
      statement.setLong(1, visaId);
      statement.execute();
      return true;
    }
    catch (SQLException excp){
      excp.printStackTrace();
      return false;
    }
  }

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

@Override
  public List<Visas> getAllFromStatement(PreparedStatement statement) throws SQLException {
    List<Visas> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      entities.add(mapper.extractFromResultSet(rs));
    }
    return entities;
  }



}
