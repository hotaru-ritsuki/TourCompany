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
public class Users {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Boolean isAdmin;
  private Countrys country;
  private List<Visas> visas;
}
