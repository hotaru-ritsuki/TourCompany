package com.softserve.tourcomp.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Bookings {
  private Long id;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer amountRooms;
  private Integer price;
  private Users user;
  private Hotels hotel;

}
