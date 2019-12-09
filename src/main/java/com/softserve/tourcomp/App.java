package com.softserve.tourcomp;

import com.softserve.tourcomp.dao.impl.JDBCUserDao;
import com.softserve.tourcomp.dto.city.CityRequest;
import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.service.*;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    ServiceFactory serviceFactory = ServiceFactory.getInstance();
//     UserService userService=serviceFactory.getUserService();
//    System.out.println(userService.findOneUser(2L));
//    CountryService countryService=serviceFactory.getCountryService();
//    System.out.println(countryService.findAll());


  }
}
