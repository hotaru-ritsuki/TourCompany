package com.softserve.tourcomp.dao;

import com.softserve.tourcomp.entity.Users;
import com.softserve.tourcomp.entity.Visas;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VisaDao extends GenericDao<Visas>{
  Optional<Visas> findById(Long visaId);
}
