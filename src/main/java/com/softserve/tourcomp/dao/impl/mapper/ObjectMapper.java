package com.softserve.tourcomp.dao.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {

  public T extractFromResultSet(ResultSet rs) throws SQLException;
}