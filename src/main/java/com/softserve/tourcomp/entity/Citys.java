package com.softserve.tourcomp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Citys {
  private Long Id;
  private String name;
  private Countrys country;
}
