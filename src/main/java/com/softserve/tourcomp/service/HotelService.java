package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.DaoFactory;
import com.softserve.tourcomp.dao.HotelDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.hotel.HotelRequest;
import com.softserve.tourcomp.dto.hotel.HotelResponse;
import com.softserve.tourcomp.entity.Citys;
import com.softserve.tourcomp.entity.Hotels;
import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;
import com.softserve.tourcomp.entity.stats.HotelStats;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
  private DaoFactory daoFactory = new JDBCDaoFactory();
  private HotelDao hotelDao = daoFactory.createHotelDao();
  private CityService cityService = new CityService();
  private BookingService bookingService=new BookingService();
  private UserService userService=new UserService();

  public Boolean create(HotelRequest hotelR) throws SQLException {
    try {
      Hotels hotel = new Hotels();
      hotel.setDiscription(hotelR.getDiscription());
      hotel.setName(hotelR.getName());
      hotel.setNumberRoom(hotelR.getNumberRoom());
      hotel.setPriceNight(hotelR.getPriceNight());
      hotel.setCity(cityService.findOneCity(hotelR.getCity()));
      return hotelDao.create(hotel);
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public Boolean update(Long id, HotelRequest hotelR) throws SQLException {
    try {
      Hotels hotel = findOneHotel(id);
      hotel.setPriceNight(hotelR.getPriceNight());
      hotel.setNumberRoom(hotelR.getNumberRoom());
      hotel.setName(hotelR.getName());
      hotel.setDiscription(hotelR.getDiscription());
      return hotelDao.update(hotel);
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findByPrice(Integer lower, Integer high) throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findHotelsByPricesPerNight(lower,high);
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findByLowerPrice(Integer lower) throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findHotelsByLowerPricePerNight(lower);
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findByHigherPrice(Integer high) throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findHotelsByHigherPricePerNight(high);
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findByCountry(Long countryId) throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findHotelsByCountryId(countryId);
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findAvailableFromToInCity(Long id, LocalDate start, LocalDate end, Long idCity) throws SQLException {
      List<HotelResponse> all =findByCity(id);
      for (HotelResponse hotel:all){
        hotel.setNumberRoom(hotel.getNumberRoom()-bookingService.bookRoom(hotel.getId(),start,end));
        if (hotel.getNumberRoom()<=0){
          all.remove(hotel);
        }
      }
      return all;
  }

  public Boolean isHotelAvailable(Long id,LocalDate start,LocalDate end) throws SQLException {
    Hotels hotel=findOneHotel(id);
    hotel.setNumberRoom(hotel.getNumberRoom()-bookingService.bookRoom(id, start, end));
    if (hotel.getNumberRoom()<=0){
      return false;
    }
    return true;
  }

  public Boolean canBooking(Long userId,LocalDate start,LocalDate end, Long hotelId,Integer number) throws SQLException {
    Hotels hotel=findOneHotel(hotelId);
    hotel.setNumberRoom(hotel.getNumberRoom()-bookingService.bookRoom(hotelId, start, end));
    if (hotel.getNumberRoom()>=number){
      Users user = userService.findOneUser(userId);
      Visas visa = hotel.getCity().getCountry().getVisa();
      if (user.getVisas().contains(visa)){
        return true;
      }else if(user.getCountry().getVisa().equals(visa)){
        return true;
      }
    }
    return false;
  }

  public List<HotelResponse> findByCity(Long cityId) throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findHotelsByCityId(cityId);
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelResponse> findAll() throws SQLException {
    try {
      List<HotelResponse> hotelR = new ArrayList<>();
      List<Hotels> hotels = hotelDao.findAll();
      for (Hotels hotel : hotels) {
        hotelR.add(hotelToHotelResponse(hotel));
      }
      return hotelR;
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public List<HotelStats> statistic() throws SQLException {
    try {
      return hotelDao.createStatistics();
    }catch (Exception e){
      throw new SQLException();
    }
  }

  public HotelResponse findOne(Long id) {
    try {
      return hotelToHotelResponse(hotelDao.findById(id).get());
    } catch (Exception e) {
      throw new IllegalArgumentException("Hotel with id " + id + " not exists");
    }
  }

  public Hotels findOneHotel(Long id) {
    return hotelDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Hotel with id " + id + " not exists"));
  }

  protected HotelResponse hotelToHotelResponse(Hotels hotel) {
    HotelResponse hotelR = new HotelResponse();
    hotelR.setId(hotel.getId());
    hotelR.setName(hotel.getName());
    hotelR.setDiscription(hotel.getDiscription());
    hotelR.setNumberRoom(hotel.getNumberRoom());
    hotelR.setPriceNight(hotel.getPriceNight());
    Citys city = hotel.getCity();
    if (city != null) {
      hotelR.setCity(cityService.cityToCityResponse(city));
    }
    return hotelR;
  }
}
