package com.softserve.tourcomp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Hotels {
  private Long id;
  private String name;
  private Integer numberRoom;
  private Integer priceNight;
  private String discription;
  private Countrys country;
  private Citys city;
}
