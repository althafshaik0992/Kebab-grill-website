//package com.example.SpringRestaurantswebsite.Config;
//
//import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
//import com.example.SpringRestaurantswebsite.Model.CustomerUser;
//import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.junit.Test;
//import org.mockito.Mock;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//
//import static org.mockito.Mockito.*;
//
//public class CustomSuccessHandlerTest {
//
//
//
//
//    @Test
//    public void testOnAuthenticationSuccess_ExistingUser() throws Exception {
//        // Mock objects
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        Authentication authentication = mock(Authentication.class);
//        DefaultOAuth2User userDetails = mock(DefaultOAuth2User.class);
//        CustomerUser existingUser = new CustomerUser();
//        CustomerUserRepository customerUserRepository = mock(CustomerUserRepository.class);
//        existingUser.setEmail("existing@example.com");
//
//        // Mock authentication behavior
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getAttribute("email")).thenReturn("existing@example.com");
//        when(customerUserRepository.findByEmail("existing@example.com")).thenReturn(existingUser);
//
//        // Call the method
//       CustomSuccessHandler handler = new CustomSuccessHandler(customerUserRepository);
//        handler.onAuthenticationSuccess(request, response, authentication);
//
//        // Verify redirect URL
//        verify(response).sendRedirect("/dashboard");
//        // You can add more verification based on your specific logic
//    }
//
//    @Test
//    public void testOnAuthenticationSuccess_NewUser() throws Exception {
//        // Mock objects
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        Authentication authentication = mock(Authentication.class);
//        DefaultOAuth2User userDetails = mock(DefaultOAuth2User.class);
//        CustomerUserRepository customerUserRepository = mock(CustomerUserRepository.class);
//
//        // Mock authentication behavior
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getAttribute("email")).thenReturn("newuser@example.com");
//        when(customerUserRepository.findByEmail("newuser@example.com")).thenReturn(null); // Simulating new user
//
//        // Call the method
//       CustomSuccessHandler handler = new CustomSuccessHandler(customerUserRepository);
//        handler.onAuthenticationSuccess(request, response, authentication);
//
//        // Verify redirect URL
//        verify(response).sendRedirect("/dashboard");
//        // Verify that userService.save() was called with the correct arguments (you need to mock userService and verify it)
//    }
//
//    @Test
//    public void testOnAuthenticationSuccess_NullPrincipal() throws Exception {
//        // Mock objects
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        Authentication authentication = mock(Authentication.class);
//        CustomerUserRepository CustomerUserRepository = mock(CustomerUserRepository.class);
//
//        // Mock authentication behavior
//        when(authentication.getPrincipal()).thenReturn(null);
//
//        // Call the method
//        CustomSuccessHandler handler = new CustomSuccessHandler(CustomerUserRepository);
//        handler.onAuthenticationSuccess(request, response, authentication);
//
//        // Verify redirect URL or any specific behavior in case of null principal
//        verify(response).sendRedirect("/default-url"); // Example default URL
//    }
//
//
//
//
//}
