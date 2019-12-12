package com.softserve.tourcomp.controller.hotel;

import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.HotelDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.entity.Hotels;
import com.softserve.tourcomp.service.HotelService;
import com.softserve.tourcomp.service.ServiceFactory;
import org.springframework.cglib.core.Local;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/hotels")
public class HotelsServlet extends HttpServlet {
  HotelService hs = ServiceFactory.getInstance().getHotelService();
  HotelDao hd = JDBCDaoFactory.getInstance().createHotelDao();
  CountryDao cd = JDBCDaoFactory.getInstance().createCountryDao();
  List<Hotels> hotels;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getAttribute("hot")==null) {
      String rel = req.getParameter("id");
      if (rel.equals("all")) {
        hotels = hd.findAll();
      } else {
        hotels = hd.findHotelsByCountryId(cd.findByCountryName(rel).get().getId());
      }
      req.setAttribute("hot", hotels);
    }
    req.getRequestDispatcher("hotelSearch.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Integer priceFrom=-1;
    Integer priceTo=100000;
    //LocalDate start = LocalDate.of(Integer.parseInt(sD[0]),Integer.parseInt(sD[1]),Integer.parseInt(sD[2]));
    //LocalDate end = LocalDate.of(Integer.parseInt(eD[0]),Integer.parseInt(eD[1]),Integer.parseInt(eD[2]));
if(req.getParameter("priceFrom")!=null){
  priceFrom=Integer.parseInt(req.getParameter("priceFrom"));
}
    if(req.getParameter("priceTo")!=null){
      priceTo=Integer.parseInt(req.getParameter("priceTo"));
    }
List<Hotels> lh=hd.findHotelsByPricesPerNight(priceFrom,priceTo);
    hotels=new ArrayList<>();
    if(!req.getParameter("id").equals("all")){
    for (Hotels hto: lh
         ) {
      if(hto.getCity().getCountry().getName().equals(req.getParameter("id"))){
        hotels.add(hto);
      }

    }}
    else{
      hotels=lh;
    }
    req.setAttribute("hot",hotels);
    req.getRequestDispatcher("hotelSearch.jsp").include(req, resp);
  }
}
