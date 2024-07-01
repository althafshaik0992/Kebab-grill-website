package com.example.SpringRestaurantswebsite.Dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRegisteredDTOTest {

    @Test
    public void testNameField() {

        UserRegisteredDTO userRegisteredDTO = new UserRegisteredDTO();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String phoneNumber = "123456789";
        String address = "address";
        String role = "user";


        userRegisteredDTO.setName(name);
        userRegisteredDTO.setEmail_id(email);
        userRegisteredDTO.setPassword(password);
        userRegisteredDTO.setPhoneNumber(phoneNumber);
        userRegisteredDTO.setAddress(address);
        userRegisteredDTO.setRole(role);

        assertEquals(name, userRegisteredDTO.getName());
        assertEquals(email, userRegisteredDTO.getEmail_id());
        assertEquals(password, userRegisteredDTO.getPassword());
        assertEquals(phoneNumber, userRegisteredDTO.getPhoneNumber());
        assertEquals(address, userRegisteredDTO.getAddress());
        assertEquals(role, userRegisteredDTO.getRole());
    }
}
