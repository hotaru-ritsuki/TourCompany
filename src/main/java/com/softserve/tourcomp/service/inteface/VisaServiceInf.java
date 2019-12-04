package com.softserve.tourcomp.service.inteface;

import com.softserve.tourcomp.dto.visa.VisaRequest;
import com.softserve.tourcomp.dto.visa.VisaResponse;
import com.softserve.tourcomp.entity.Visas;

import java.util.List;

public interface VisaServiceInf {
  boolean create(VisaRequest visa);

  Visas findOneVisa(Long id);

  List<VisaResponse> findAll();

  VisaResponse findOne(Long id);
}
