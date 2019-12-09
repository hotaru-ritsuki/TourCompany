package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.BookingDao;
import com.softserve.tourcomp.dao.DaoFactory;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.booking.BookingRequest;
import com.softserve.tourcomp.dto.booking.BookingResponse;
import com.softserve.tourcomp.entity.Bookings;
import com.softserve.tourcomp.entity.Hotels;
import com.softserve.tourcomp.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService {
  private DaoFactory daoFactory = new JDBCDaoFactory();
  private BookingDao bookingDao = daoFactory.createBookingDao();
  private UserService userService = new UserService();
  private HotelService hotelService = new HotelService();

  public Boolean create(BookingRequest bookingR) throws SQLException {
    try {
      Bookings book = new Bookings();
      book.setAmountRooms(bookingR.getAmountRooms());
      book.setStartDate(bookingR.getStartDate());
      book.setEndDate(bookingR.getEndDate());
      book.setAmountRooms(bookingR.getAmountRooms());
      book.setUser(userService.findOneUser(bookingR.getId_user()));
      book.setHotel(hotelService.findOneHotel(bookingR.getId_hotel()));
      book.setPrice(bookingR.getAmountRooms() * book.getHotel().getPriceNight());
      return bookingDao.create(book);
    } catch (Exception e) {
      throw new SQLException();
    }
  }

  public boolean update(Long id, BookingRequest bookingR) {
    return false;
  }

  public Bookings findOneBook(Long id) {
    return bookingDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Booking with id " + id + " not exists"));
  }

  public BookingResponse findOne(Long id) {
    try {
      Optional<Bookings> byId = bookingDao.findById(id);
      Bookings bookings = byId.get();
      return bookingToBookingResponse(bookings);
    } catch (Exception e) {
      throw new IllegalArgumentException("Booking with id " + id + " not exists");
    }
  }

  public List<BookingResponse> findAll(Long id) throws SQLException {
    try{
      List<BookingResponse> bookList=new ArrayList<>();
      List<Bookings> all = bookingDao.findAll();
      for (Bookings book:all){
        bookList.add(bookingToBookingResponse(book));
      }
      return bookList;
    }catch (Exception e){
      throw new SQLException();
    }
  }


  protected BookingResponse bookingToBookingResponse(Bookings bookings) {
    BookingResponse br = new BookingResponse();
    br.setId(bookings.getId());
    br.setAmountRooms(bookings.getAmountRooms());
    br.setStartDate(bookings.getStartDate());
    br.setEndDate(bookings.getStartDate());
    br.setPrice(br.getAmountRooms());
    Hotels hotel = bookings.getHotel();
    if (hotel != null) {
      br.setHotel(hotelService.hotelToHotelResponse(hotel));
    }
    Users user = bookings.getUser();
    if (user != null) {
      br.setUser(userService.userToUserResponse(user));
    }
    return br;
  }
}
