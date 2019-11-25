package com.softserve.tourcomp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Bookings {
  private Long id;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer amountRooms;
  private Integer price;
  private Users user;
  private Hotels hotel;

}
