package com.softserve.tourcomp.service;

public class ServiceFactory {
  private BookingService bookingService;
  private CityService cityService;
  private CountryService countryService;
  private HotelService hotelService;
  private UserService userService;
  private VisaService visaService;

  private static ServiceFactory instance;

  private ServiceFactory(){

  }

  public static ServiceFactory getInstance(){
    if (instance==null){
      instance=new ServiceFactory();
    }
    return instance;
  }

  public BookingService getBookingService() {
    if (bookingService == null) {
      if (bookingService == null) {
        bookingService = new BookingService();
      }
    }
    return bookingService;
  }

  public CityService getCityService() {
    if (cityService == null) {
      if (cityService == null) {
        cityService = new CityService();
      }
    }
    return cityService;
  }

  public CountryService getCountryService() {
    if (countryService == null) {
      if (countryService == null) {
        countryService = new CountryService();
      }
    }
    return countryService;
  }

  public HotelService getHotelService() {
    if (hotelService == null) {
      if (hotelService == null) {
        hotelService = new HotelService();
      }
    }
    return hotelService;
  }

  public UserService getUserService() {
    if (userService == null) {
        if (userService == null) {
          userService = new UserService();
        }
      }
    return userService;
  }

  public VisaService getVisaService() {
    if (visaService == null) {
        if (visaService == null) {
          visaService = new VisaService();
        }
      }
    return visaService;
  }

}
