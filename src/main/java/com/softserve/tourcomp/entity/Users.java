package com.softserve.tourcomp.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
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
