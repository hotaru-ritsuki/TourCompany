package com.softserve.tourcomp;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.entity.Countrys;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JDBCDaoFactory daoFactory=new JDBCDaoFactory();
        CountryDao countryDao=daoFactory.createCountryDao();
        System.out.println(countryDao.findById(1L));

    }
}
