package com.softserve.tourcomp.dto.booking;

import java.time.LocalDate;

public class BookingRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer amountRooms;
  private Long id_user;
  private Long id_hotel;
}
