//package com.example.SpringRestaurantswebsite.Controller;
//
//import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
//import com.example.SpringRestaurantswebsite.Model.CustomerUser;
//import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
//import com.example.SpringRestaurantswebsite.Service.CustomerDetailsServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.Model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class LoginControllerTest {
//
//
//
//    private CustomerDetailsServiceImpl customerDetailsService; // Mocking the service dependency
//
//    private LoginController loginController;
//
//    @BeforeEach
//    public void setup() {
//        // Initialize the controller with mocked dependencies
//        loginController = new LoginController();
//        customerDetailsService = new CustomerDetailsServiceImpl();//
//        MockitoAnnotations.initMocks(this);// Instantiate your controller
//        // Alternatively, you can use mocking framework to mock dependencies
//    }
//
//    @Test
//    public void testLogin() {
//        // Mock Model object
//        Model model = mock(Model.class);
//
//        // Call the controller method
//        String viewName = loginController.login();
//
//        // Assert the returned view name
//        assertEquals("login", viewName);
//
//        // Optionally, verify interactions with the model if needed
//        // verify(model).addAttribute(...);
//    }
//
//
//
//
//    @Test
//    public void testLoginUser() {
//        // Mock data for CustomerUser
//        CustomerUserDto userDto = new CustomerUserDto();
//        userDto.setUsername("testuser@gmail.com");
//        userDto.setPassword("testpassword");
//
//        // Mock behavior of customerDetailsService.loadUserByUsername method
//        when(customerDetailsService.loadUserByUsername("testuser")).thenReturn(null); // Example: return null for simplicity
//
//        // Call the controller method
//        loginController.loginUser(userDto);
//
//        // Verify that customerDetailsService.loadUserByUsername method was called with correct arguments
//        verify(customerDetailsService).loadUserByUsername("testuser");
//
//        // You can add more assertions based on your specific requirements
//    }
//}
