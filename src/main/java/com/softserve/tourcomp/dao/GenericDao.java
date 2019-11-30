package com.softserve.tourcomp.dao;

import java.util.List;
import java.util.Optional;

  public interface GenericDao<E> extends AutoCloseable{
    boolean create(E entity);
    List<E> findAll();
    List<E> findRange(int start, int count);
    Optional<E> findById(long id);
    long count();
    boolean update(E entity);
    boolean delete(long id);
    void close();
  }

