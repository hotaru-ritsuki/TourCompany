package com.softserve.tourcomp.service;

import com.softserve.tourcomp.dao.VisaDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.dto.visa.VisaRequest;
import com.softserve.tourcomp.dto.visa.VisaResponse;
import com.softserve.tourcomp.entity.Visas;
import com.softserve.tourcomp.service.inteface.VisaServiceInf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VisaService implements VisaServiceInf {
  private JDBCDaoFactory daoFactory = new JDBCDaoFactory();
  private VisaDao visaDao = daoFactory.createVisaDao();

  @Override
  public boolean create(VisaRequest visa) {
    Visas visas = new Visas();
    visas.setName(visa.getName());
    return visaDao.create(visas);
  }

  @Override
  public Visas findOneVisa(Long id) {
    return visaDao.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Car with id " + id + " not exists"));
  }

  @Override
  public List<VisaResponse> findAll() {
    List<VisaResponse> list = new ArrayList<>();
    List<Visas> visas = visaDao.findAll();
    for (Visas visa : visas) {
      list.add(visaToVisaResponse(visa));
    }
    return list;
  }

  @Override
  public VisaResponse findOne(Long id) {
    Optional<Visas> byId = visaDao.findById(id);
    if (byId.isPresent()) {
      Visas visas = byId.get();
      return visaToVisaResponse(visas);
    }
    new IllegalArgumentException("User with id " + id + " not exists");
    return null;
  }

  protected VisaResponse visaToVisaResponse(Visas visa) {
    VisaResponse vr = new VisaResponse();
    vr.setId(visa.getId());
    vr.setName(visa.getName());
    return vr;
  }
}
