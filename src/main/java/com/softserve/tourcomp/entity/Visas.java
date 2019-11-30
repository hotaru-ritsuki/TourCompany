package com.softserve.tourcomp.entity;

import lombok.*;

import java.util.List;

@Data
public class Visas {
  private Long Id;
  private String name;
  private Countrys country;
  private List<Users> users;
}
