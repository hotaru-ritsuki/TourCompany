package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.impl.JDBCCountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dao.impl.JDBCUserDao;
import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.dto.user.UserResponse;
import com.softserve.tourcomp.dto.visa.VisaResponse;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
  private JDBCDaoFactory daoFactory = new JDBCDaoFactory();
  private JDBCUserDao userDao = daoFactory.createUserDao();
  private JDBCCountryDao countryDao = daoFactory.createCountryDao();
  private CountryService countryService = new CountryService();
  private VisaService visaService = new VisaService();

  public void create(UserRequest userRequest) {
    Users user = new Users();
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setEmail(userRequest.getEmail());
    user.setIsAdmin(false);
    user.setPassword(userRequest.getPassword());
    user.setCountry(countryDao.findById(userRequest.getCountry()).get());
    userDao.create(user);
  }

  public UserResponse findOne(Long id) throws IllegalArgumentException {
    Optional<Users> byId = userDao.findById(id);
    if (byId.isPresent()) {
      Users users = byId.get();
      return userToUserResponse(users);
    }
    new IllegalArgumentException("User with id " + id + " not exists");
    return null;
  }

  public Users findOneUser(Long id) {
    return userDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not exists"));
  }

  public List<UserResponse> findAll() {
    List<UserResponse> list = new ArrayList<>();
    List<Users> users = userDao.findAll();
    for (Users user : users) {
      list.add(userToUserResponse(user));
    }
    return list;
  }

  public List<Users> findAllUsers() {
    try {
      return userDao.findAll();
    } catch (Exception e) {
      throw new NullPointerException();
    }
  }

  public UserResponse findUserByEmail(String email) {
    try {
      Optional<Users> userByEmail = userDao.findUserByEmail(email);
      return userToUserResponse(userByEmail.get());
    } catch (Exception e) {
      throw new NullPointerException("User with " + email + " don't found");
    }
  }

  private UserResponse userToUserResponse(Users user) {
    UserResponse ur = new UserResponse();
    ur.setId(user.getId());
    ur.setEmail(user.getEmail());
    ur.setFirstName(user.getFirstName());
    ur.setLastName(user.getLastName());
    ur.setIsAdmin(user.getIsAdmin());
    Countrys country = user.getCountry();
    if (country != null) {
      ur.setCountry(countryService.findOne(country.getId()));
    }
    List<Visas> list = user.getVisas();
    if (!list.isEmpty()) {
      List<VisaResponse> listr = new ArrayList<>();
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i) != null) {
          listr.add(visaService.visaToVisaResponse(list.get(i)));
        }
      }
      ur.setVisas(listr);
    }
    return ur;
  }


}
