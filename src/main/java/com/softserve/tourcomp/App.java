package com.softserve.tourcomp;

import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.service.CountryService;
import com.softserve.tourcomp.service.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserService userService=new UserService();
        System.out.println(userService.findAll());

//        UserRequest userRequest=new UserRequest();
//        userRequest.setFirstName("asfd");
//        userRequest.setLastName("sfdf");
//        userRequest.setEmail("sdfhvsnbd");
//        userRequest.setPassword("sfad");
//        userRequest.setCountry(1L);
//        userService.create(userRequest);
//        CountryService countryService=new CountryService();
//        System.out.println(countryService.findOne(1L));
//        CountryRequest countryRequest=new CountryRequest();
//        countryRequest.setName("adsa");
//        countryRequest.setVisa(1L);
//        countryService.create(countryRequest);
//        JDBCDaoFactory daoFactory=new JDBCDaoFactory();
//        CountryDao countryDao=daoFactory.createCountryDao();
//        System.out.println(countryDao.findById(1L).get().getVisa());

    }
}
