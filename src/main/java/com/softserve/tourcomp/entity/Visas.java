package com.softserve.tourcomp.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Visas {
  private Long Id;
  private String name;
  private Countrys country;
  private List<Users> users;
}
