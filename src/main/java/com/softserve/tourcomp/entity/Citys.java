package com.softserve.tourcomp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Citys {
  private Long Id;
  private String name;
  private Countrys country;
}
