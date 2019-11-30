package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.UserDao;
import com.softserve.tourcomp.dao.impl.mapper.UserMapper;
import com.softserve.tourcomp.entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCUserDao extends JDBCGenericDao<Users> implements UserDao {
  public JDBCUserDao(Connection connection) {
    super(connection,"INSERT INTO USERS (firstName, lastName, email, password, isAdmin, id_country) VALUES (?, ?, ?, ?, ?, ?);",
            "SELECT * FROM USERS WHERE Id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM USERS LIMIT ?,?",
            "SELECT * FROM USERS",
            "SELECT COUNT(*) FROM USERS",
            "COUNT(*)",
            "UPDATE USERS SET firstName = ?, lastName = ?, email = ?, password = ?, isAdmin = ?, id_count = ? WHERE id = ?",
            7,
            "DELETE FROM USERS WHERE id = ?",
            new UserMapper());
  }

  @Override
  public Optional<Users> findByEmail(String email) {
    return Optional.empty();
  }


  @Override
  public Optional<Users> findById(Long usrId) {
    return Optional.empty();
  }

  @Override
  public long countUsers() {
    return 0;
  }

  @Override
  long getId(Users entity) {
    return 0;
  }

  @Override
  void setId(Users entity, long Id) throws SQLException {

  }

  @Override
  void setEntityValues(PreparedStatement statement, Users entity) throws SQLException {

  }
}
