package com.softserve.tourcomp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.tourcomp.dto.country.CountryRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/countrys")
public class CountryController extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
    String json = "";
    if(br != null){
      json = br.readLine();
      System.out.println(json);
    }
    ObjectMapper mapper = new ObjectMapper();
    CountryRequest countryRequest=mapper.readValue(json, CountryRequest.class);
    resp.setContentType("countrys/json");

    mapper.writeValue(resp.getOutputStream(), countryRequest);
  }
}
