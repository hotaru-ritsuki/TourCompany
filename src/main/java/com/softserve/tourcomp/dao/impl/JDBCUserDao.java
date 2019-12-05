package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.UserDao;
import com.softserve.tourcomp.dao.impl.mapper.CountryMapper;
import com.softserve.tourcomp.dao.impl.mapper.ObjectMapper;
import com.softserve.tourcomp.dao.impl.mapper.UserMapper;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCGenericDao<Users> implements UserDao {
  private CountryMapper countryMapper = new CountryMapper();
  private VisaMapper visaMapper = new VisaMapper();
  private final String FindUserByEmailQuery = "SELECT * FROM USERS WHERE email = ?";
  private final String FindUserByUsernameQuery = "SELECT * FROM USERS WHERE lastName = ?";
  private final String findUsersByCountryIdQuery = "SELECT * FROM USERS WHERE id_country = ?";
  private final String findUsersByVisaIdQuery = "SELECT * FROM USERS_VISAS LEFT JOIN USERS ON USERS.id = USERS_VISAS.id_user WHERE id_visa = ?";
  private final String FindUserByBookingIdQuery = "SELECT * FROM BOOKINGS LEFT JOIN USERS ON BOOKINGS.id_user = USERS.id WHERE BOOKINGS.id = ?";
  private final String createVisasQuery = "INSERT INTO USERS_VISAS(id_user, id_visa) VALUES (?,?)";
  private final String deleteVisasQuery = "DELETE FROM USERS_VISAS WHERE id_user = ?";
  private final String FindVisasQuery = "SELECT * FROM USERS_VISAS LEFT JOIN VISAS ON VISAS.id = id_visa WHERE id_user = ?";
  private final String FindCountryByUserIdQuery = "SELECT * FROM USERS LEFT JOIN COUNTRYS ON USERS.id_country=COUNTRYS.id WHERE USERS.id = ?";

  public JDBCUserDao(Connection connection) {
    super(connection,"INSERT INTO USERS (firstName, lastName, email, password, isAdmin, id_country) VALUES (?, ?, ?, ?, ?, ?);",
            "SELECT * FROM USERS WHERE id = ?",
            "SELECT * FROM USERS ",
            "SELECT COUNT(*) FROM USERS ",
            "COUNT(*)",
            "UPDATE USERS SET firstName = ?, lastName = ?, email = ?, password = ?, isAdmin = ?, id_country = ? WHERE id = ?",
            7,
            "DELETE FROM USERS WHERE id = ?",
            new UserMapper());
  }

 /* public static void main(String[] args) {
   JDBCDaoFactory jdbcDaoFactory =  new JDBCDaoFactory();
    JDBCUserDao jdbcUserDao=jdbcDaoFactory.createUserDao();
    List<Visas> visas= new ArrayList();
    visas.add(new Visas(1L,"sdsf"));
    Users user=new Users(15L,"ЙОбаний","V rot","asasfsfsdf@gmail.com","daosfksdf",true,new Countrys(6L,"Ukraine",new Visas(1L,"sdsf")),new ArrayList());
  jdbcUserDao.create(user);
    System.out.println(user.getId());

   System.out.println(jdbcUserDao.findById(3L).get());
    for (Users users: jdbcUserDao.findUsersByCountryId(3L)
         ) {
      System.out.println(users);

    }


    System.out.println(jdbcUserDao.findUserByVisaId(3L));


   System.out.println(jdbcUserDao.count());
jdbcUserDao.delete(5L);
   System.out.println(jdbcUserDao.findUserByEmail("sfsfsf").get());
    System.out.println(jdbcUserDao.findUserByName("gxgx").get());

  }
*/
@Override
public boolean update(Users user){
  boolean created = false;
  try (PreparedStatement statement = connection.prepareStatement(UpdateQuery)){
    deleteVisas(user);
    created = updateAction(statement,user);
    insertVisas(user);
  } catch (Exception ex) {
    ex.printStackTrace();
  }
  return created;
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

    try (PreparedStatement statement = connection.prepareStatement(FindByIDQuery)) {
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

  public Optional<Users> findUserByName(String lastName) {
    Users entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindUserByUsernameQuery)) {
      statement.setString(1, lastName);
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
      found = getAllFromStatement(statement);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return found;
  }

  @Override
  public List<Users> getAllFromStatement(PreparedStatement statement) throws SQLException {
    ObjectMapper<Users> userMapper = new UserMapper();
    List<Users> entities = new ArrayList<>();
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      Users extracted =userMapper.extractFromResultSet(rs);
      extracted.setCountry(getCountry(extracted.getId()));
      extracted.setVisas(getVisas(extracted.getId()));
      entities.add(extracted);
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
      found = getAllFromStatement(statement);
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
  Users extractEntity(ResultSet rs) throws SQLException {
    Users extracted = mapper.extractFromResultSet(rs);
    extracted.setCountry(getCountry(extracted.getId()));
    extracted.setVisas(getVisas(extracted.getId()));
    return extracted;
  }

  private Countrys getCountry(Long id) {
    Countrys entity = null;

    try (PreparedStatement statement = connection.prepareStatement(FindCountryByUserIdQuery)) {
      statement.setLong(1, id);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        entity = countryMapper.extractFromResultSet(result);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Optional.ofNullable(entity).get();
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
  void setEntityValues(PreparedStatement statement, Users entity) throws SQLException {
    statement.setString(1,entity.getFirstName());
    statement.setString(2,entity.getLastName());
    statement.setString(3,entity.getEmail());
    statement.setString(4,entity.getPassword());
    statement.setBoolean(5,entity.getIsAdmin());
    statement.setLong(6,entity.getCountry().getId());
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
        insertVisas.execute();
      }
    }
  }

  private boolean deleteVisas(Users user) throws SQLException {
    return deleteVisas(user.getId());
  }
  private boolean deleteVisas(long userId) throws SQLException {
    boolean executed=false;
    try (PreparedStatement statement = connection.prepareStatement(deleteVisasQuery)) {
      statement.setLong(1, userId);
      statement.execute();
      executed=true;
    }
    return executed;
  }
  private List<Visas> getVisas(long userId) throws SQLException {
    List<Visas> result = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(FindVisasQuery)) {
      statement.setLong(1,userId);
      ResultSet rs =  statement.executeQuery();
      while (rs.next()) {
        result.add(visaMapper.extractFromResultSet(rs));
      }
    }
    return result;
  }
}
