package com.softserve.tourcomp.dao.impl;

import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.mapper.VisaMapper;
import com.softserve.tourcomp.entity.Visas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCVisaDao extends JDBCGenericDao<Visas> implements VisaDao {
  public JDBCVisaDao(Connection connection) {
    super(connection,"INSERT INTO VISAS (name, id_country) VALUES (?, ?)",
            "SELECT * FROM VISAS WHERE id = ?",
            "SELECT SQL_CALC_FOUND_ROWS * FROM VISAS LIMIT ?,?",
            "SELECT * FROM VISAS",
            "SELECT COUNT(*) FROM VISAS",
            "COUNT(*)",
            "UPDATE VISAS SET name = ?, id_country = ? WHERE id= ?",
            3,
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

  }

  @Override
  public boolean create(Visas entity) {
    return false;
  }

  @Override
  public boolean update(Visas entity) {
    return false;
  }
}
