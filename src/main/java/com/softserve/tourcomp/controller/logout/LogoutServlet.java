package com.softserve.tourcomp.controller.logout;

import com.softserve.tourcomp.constants.PathToPage;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathToPage.LOGOUT_PATH)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        req.getSession(true).removeAttribute("password");
        req.getSession(true).removeAttribute("login");
        req.getSession(true).removeAttribute("session");
        resp.sendRedirect(PathToPage.HOME_PATH);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
