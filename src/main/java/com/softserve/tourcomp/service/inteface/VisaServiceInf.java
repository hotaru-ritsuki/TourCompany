package com.softserve.tourcomp.service.inteface;

import com.softserve.tourcomp.dto.visa.VisaRequest;
import com.softserve.tourcomp.entity.Visas;

public interface VisaServiceInf {
  public void create(VisaRequest visa);
}
