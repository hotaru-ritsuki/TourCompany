package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.visa.VisaRequest;
import com.softserve.tourcomp.dto.visa.VisaResponse;
import com.softserve.tourcomp.entity.Visas;
import com.softserve.tourcomp.service.inteface.VisaServiceInf;

public class VisaService implements VisaServiceInf {
  private JDBCDaoFactory daoFactory=new JDBCDaoFactory();
  private VisaDao visaDao = daoFactory.createVisaDao();

  @Override
  public void create(VisaRequest visa) {

  }

  public Visas findOneVisa(Long id){
    return visaDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Car with id " + id + " not exists"));
  }

//  public VisaResponse findOne(Long id){
//
//  }

  protected VisaResponse visaToVisaResponse(Visas visa){
    VisaResponse vr=new VisaResponse();
    vr.setId(visa.getId());
    vr.setName(visa.getName());
    return vr;
  }
}
