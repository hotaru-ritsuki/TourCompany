package com.softserve.tourcomp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Users {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Boolean isAdmin;
}
