package com.softserve.tourcomp.dao.impl.mapper;

import com.softserve.tourcomp.entity.Bookings;
import com.softserve.tourcomp.entity.Hotels;
import com.softserve.tourcomp.entity.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingMapper implements ObjectMapper<Bookings> {

  @Override
  public Bookings extractFromResultSet(ResultSet rs) throws SQLException {
    Bookings booking = new Bookings();
    HotelMapper hotel = new HotelMapper();
    UserMapper user = new UserMapper();
    booking.setId(rs.getLong("BOOKINGS.id"));
    booking.setAmountRooms(rs.getInt("BOOKINGS.amountRooms"));
    booking.setPrice(rs.getInt("BOOKINGS.price"));
    booking.setStartDate(rs.getDate("BOOKINGS.startDate").toLocalDate());
    booking.setEndDate(rs.getDate("BOOKINGS.endDate").toLocalDate());
    try {
      booking.setHotel(hotel.extractFromResultSet(rs));
      booking.setUser(user.extractFromResultSet(rs));
    } catch (SQLException e) {
      booking.setHotel(new Hotels());
      booking.setUser(new Users());
    }
    return booking;
  }
}
