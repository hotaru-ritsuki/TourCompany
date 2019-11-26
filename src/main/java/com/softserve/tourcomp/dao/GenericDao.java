package com.softserve.tourcomp.dao;

import java.util.List;
import java.util.Optional;

  public interface GenericDao<T> extends AutoCloseable{
    boolean create(T entity);
    List<T> findAll();
    List<T> findRange(int start, int count);
    Optional<T> findById(long id);
    long count();
    boolean update(T entity);
    boolean delete(long id);
    void close();
  }

