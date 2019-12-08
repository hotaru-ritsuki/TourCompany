package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.stats.UserStats;

import java.util.Optional;
import java.util.List;

/**
 *
 */
public interface UserDao extends GenericDao<Users>{
  /**
   *
   * @param email
   * @return
   */
  Optional<Users> findUserByEmail(String email);

  /**
   *
   * @param usrId
   * @return
   */
  Optional<Users> findById(Long usrId);

  /**
   *
   * @param lastName
   * @return
   */
  Optional<Users> findUserByName(String lastName);

  /**
   *
   * @param CountryId
   * @return
   */
  List<Users> findUsersByCountryId(Long CountryId);

  /**
   *
   * @param bookingId
   * @return
   */
  Optional<Users> findUserByBookingId(Long bookingId);

  /**
   *
   * @param visaId
   * @return
   */
  List<Users> findUserByVisaId(Long visaId);

  /**
   *
   * @return
   */
  List<UserStats> createStatistics();
}
