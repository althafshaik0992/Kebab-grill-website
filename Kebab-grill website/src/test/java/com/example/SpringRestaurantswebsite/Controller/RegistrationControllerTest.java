package com.example.SpringRestaurantswebsite.Controller;

import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.*;

public class RegistrationControllerTest {


    private CustomerDetailsService customerDetailsService;

    private RegistrationController registrationController;

    private RedirectAttributes redirectAttributes;



    @BeforeEach
    public void setup() {
        customerDetailsService = mock(CustomerDetailsService.class);
        redirectAttributes = mock(RedirectAttributes.class);
        registrationController = new RegistrationController(customerDetailsService);
    }

    @Test
    public void testUserRegistrationDto() {
        UserRegisteredDTO dto = registrationController.userRegistrationDto();

        assertNotNull(dto);
        // Add more assertions as per your DTO structure and expectations
    }

    @Test
    public void testRegister() {
        String viewName = registrationController.register();

        assertEquals("register", viewName);
        // Add more assertions if necessary, e.g., model attributes being set
    }

    @Test
    public void testRegisterUserAccount_EmailExists() {
        // Mock data
        UserRegisteredDTO dto = new UserRegisteredDTO();
        dto.setEmail_id("existing@example.com");

        BindingResult bindingResult = new BeanPropertyBindingResult(dto, "registrationDto");
        bindingResult.rejectValue("email_id", "error.email_id", "Email already in use");

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        // Mocking exists method to return true for existing email
        when(customerDetailsService.exists("existing@example.com")).thenReturn(true);

        // Test
        String viewName = registrationController.registerUserAccount(dto, bindingResult, redirectAttributes);

        assertEquals("register", viewName); // Should return to registration page
        assertEquals("Email Already In use", redirectAttributes.getFlashAttributes().get("message"));
        verify(customerDetailsService, never()).save(any(UserRegisteredDTO.class));
    }

    @Test
    public void testRegisterUserAccount_SuccessfulRegistration() {
        // Mock data
        UserRegisteredDTO dto = new UserRegisteredDTO();
        dto.setEmail_id("new@example.com");

        BindingResult bindingResult = new BeanPropertyBindingResult("registrationDto", "registrationDto");

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        // Mocking exists method to return false for new email
        when(customerDetailsService.exists("new@example.com")).thenReturn(false);

        // Test
        String viewName = registrationController.registerUserAccount(dto, bindingResult, redirectAttributes);

        assertEquals("redirect:/login", viewName); // Should redirect to login page
        assertNotNull((Object) "Your Account Registered Successfully", (String) redirectAttributes.getFlashAttributes().get("message"));
        verify(customerDetailsService, times(1)).save(dto);
    }












}
