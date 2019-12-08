package com.softserve.tourcomp;

import com.softserve.tourcomp.dto.city.CityRequest;
import com.softserve.tourcomp.dto.country.CountryRequest;
import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.service.CityService;
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
        CityService cityService=new CityService();
        System.out.println(cityService.findOne(1L));
//        cityService.create(new CityRequest("Lviv",1L));

//        CountryService countryService=new CountryService();
//        System.out.println(countryService.findOneCountry(1L));


    }
}
