package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Users;
import java.util.Optional;
import java.util.List;

public interface UserDao extends GenericDao<Users>{

  Optional<Users> findUserByEmail(String email);

  Optional<Users> findById(Long usrId);

  Optional<Users> findUserByName(String firstName, String lastName);

  List<Users> findUsersByCountryId(Long CountryId);

  Optional<Users> findUserByBookingId(Long bookingId);

  List<Users> findUserByVisaId(Long visaId);
}
