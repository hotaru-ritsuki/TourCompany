package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Bookings;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDao extends GenericDao<Bookings>{

  List<Bookings> findByUserEmail(String email);

  List<Bookings> findBookingsByHotelIdAndDate(Long hotelId, LocalDate fromDate);

  List<Bookings> findBookingsByHotelIdAndDate(Long hotelId, LocalDate fromDate,LocalDate endDate);

  List<Bookings> findBookingsByCityId(Long cityId);

  List<Bookings> findBookingsByCityId(Long cityId, LocalDate fromDate);

  List<Bookings> findBookingsByCountryId(Long countryId, LocalDate fromDate);

  List<Bookings> findBookingsByCountryId(Long countryId);

  List<Bookings> findBookingsByUserId(Long userId);

  List<Bookings> findBookingsByCountryId(Long countryId, LocalDate fromDate, LocalDate endDate);

}
