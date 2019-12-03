package com.softserve.tourcomp.dto.country;

import com.softserve.tourcomp.entity.Visas;
import lombok.Data;

@Data
public class CountryResponse {
  private Long Id;
  private String name;
  private Visas visa;
}
