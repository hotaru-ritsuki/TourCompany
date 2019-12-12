package com.softserve.tourcomp.controller.hotel;

import com.softserve.tourcomp.constants.PathToJsp;
import com.softserve.tourcomp.dao.CountryDao;
import com.softserve.tourcomp.dao.impl.JDBCCountryDao;
import com.softserve.tourcomp.dao.impl.JDBCDaoFactory;
import com.softserve.tourcomp.service.HotelService;
import com.softserve.tourcomp.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/hotels")
public class HotelsServlet extends HttpServlet {
  HotelService hs= ServiceFactory.getInstance().getHotelService();
  CountryDao cd= JDBCDaoFactory.getInstance().createCountryDao();
  @Override
  public void init() throws ServletException {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String rel=req.getParameter("id");
      if(rel.equals("all"))
      req.setAttribute("hot",hs.findAll());
      else{
        req.setAttribute("hot",hs.findByCountry( cd.findByCountryName(rel).get().getId()));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    req.getRequestDispatcher("hotelSearch.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
