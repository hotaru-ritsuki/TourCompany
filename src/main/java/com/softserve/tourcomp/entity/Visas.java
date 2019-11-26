package com.softserve.tourcomp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Visas {
  private Long Id;
  private String name;
  private Countrys country;
  private List<Users> users;
}
