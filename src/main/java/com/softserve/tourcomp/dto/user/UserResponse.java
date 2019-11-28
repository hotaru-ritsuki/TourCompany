package com.softserve.tourcomp.dto.user;

import com.softserve.tourcomp.entity.Countrys;
import com.softserve.tourcomp.entity.Visas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private Boolean isAdmin;
  private Countrys country;
  private List<Visas> visas;
}
