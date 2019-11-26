package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Users;
import java.util.Optional;
import java.util.List;

public interface UserDao extends GenericDao<Users>{
  Optional<Users> findByEmail(String email);
  long countUsers();
}
