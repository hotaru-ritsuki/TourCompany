package com.softserve.tourcomp.entity;

import lombok.*;

@Data
public class Hotels {
  private Long id;
  private String name;
  private Integer numberRoom;
  private Integer priceNight;
  private String discription;
  private Citys city;
}
