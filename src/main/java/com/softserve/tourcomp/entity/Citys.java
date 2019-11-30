package com.softserve.tourcomp.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Citys {
  private Long Id;
  private String name;
  private Countrys country;
}
