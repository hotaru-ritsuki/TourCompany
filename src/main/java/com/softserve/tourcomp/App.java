package com.softserve.tourcomp;


import com.softserve.tourcomp.dto.user.UserRequest;
import com.softserve.tourcomp.service.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserService userService=new UserService();
        UserRequest userRequest=new UserRequest();
        userRequest.setFirstName("asfd");
        userRequest.setLastName("sfdf");
        userRequest.setEmail("sdfhvsnbd");
        userRequest.setPassword("sfad");
        userRequest.setCountry(1L);
        userService.create(userRequest);

    }
}
