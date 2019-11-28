package com.softserve.tourcomp.dto.user;

import com.softserve.tourcomp.entity.Countrys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Countrys country;
}
