package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.DaoFactory;
import com.softserve.tourcomp.dao.UserDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.entity.Users;

public class UserService {
  private JDBCDaoFactory daoFactory=new JDBCDaoFactory();
  private UserDao userDao= daoFactory.createUserDao();
  private CountryDao countryDao=daoFactory.createCountryDao();

  public void create(UserRequest userRequest){
    Users user =new Users();
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setEmail(userRequest.getEmail());
    user.setIsAdmin(false);
    user.setPassword(userRequest.getPassword());
    user.setCountry(countryDao.findById(userRequest.getCountry()).get());
    userDao.create(user);
  }

}
