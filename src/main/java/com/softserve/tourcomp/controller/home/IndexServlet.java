package com.softserve.tourcomp.controller.home;

import com.softserve.tourcomp.constants.PathToJsp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {
  @Override
  public void init() throws ServletException {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("session",req.getParameter("session"));
    req.getRequestDispatcher(PathToJsp.HOME_JSP).forward(req, resp);
  }
}
