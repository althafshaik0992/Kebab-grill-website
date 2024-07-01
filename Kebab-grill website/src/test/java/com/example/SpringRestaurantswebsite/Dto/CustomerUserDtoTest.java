package com.example.SpringRestaurantswebsite.Dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerUserDtoTest {


    @Test
    public void testNameField() {
        CustomerUserDto customerUserDto = new CustomerUserDto();
       String userName= "test";
       String password= "test";
       int otp = 123;


       customerUserDto.setUsername(userName);
       customerUserDto.setPassword(password);
       customerUserDto.setOtp(otp);

        assertEquals(userName, customerUserDto.getUsername());
        assertEquals(password, customerUserDto.getPassword());
        assertEquals(otp, customerUserDto.getOtp());


    }
}
