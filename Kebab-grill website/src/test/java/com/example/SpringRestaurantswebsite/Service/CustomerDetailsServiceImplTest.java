//package com.example.SpringRestaurantswebsite.Service;
//
//import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
//import com.example.SpringRestaurantswebsite.Model.CustomerUser;
//import com.example.SpringRestaurantswebsite.Model.PasswordResetToken;
//import com.example.SpringRestaurantswebsite.Model.Role;
//import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
//import com.example.SpringRestaurantswebsite.Repository.RoleRepository;
//import com.example.SpringRestaurantswebsite.Repository.TokenRepository;
//import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
//import com.example.SpringRestaurantswebsite.Service.CustomerDetailsServiceImpl;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.mail.*;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//public class CustomerDetailsServiceImplTest {
//
//    @Mock
//    private CustomerUserRepository customerUserRepository;
//
//    @InjectMocks
//    private CustomerDetailsServiceImpl userDetailsService;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void setUp() {
//        // Inject the mock repository into the service
//        ReflectionTestUtils.setField(userDetailsService, "customerUserRepository", customerUserRepository);
//       // ReflectionTestUtils.setField(roleRepository, "roleRepository", roleRepository);
//    }
//
//
//    @Test
//    public void testLoadUserByUsername_ValidUser() {
//        // Create a set of roles for the mock user
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role("ROLE_USER"));
//
//        // Mocking behavior of customerUserRepository
//        CustomerUser mockUser = new CustomerUser("test@example.com", "password", roles);
//        when(customerUserRepository.findByEmail("test@example.com")).thenReturn(mockUser);
//
//        // Call the method under test
//        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
//
//        // Assertions
//        assertEquals("test@example.com", userDetails.getUsername());
//        assertEquals("password", userDetails.getPassword());
//
//        // Convert roles to authorities using SimpleGrantedAuthority
//        Set<SimpleGrantedAuthority> expectedAuthorities = new HashSet<>();
//        for (Role role : roles) {
//            expectedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//
//        assertEquals(expectedAuthorities, userDetails.getAuthorities());
//
//        // Verify repository method was called
//        verify(customerUserRepository, times(1)).findByEmail("test@example.com");
//    }
//
//    @Test
//    public void testLoadUserByUsername_UserNotFound() {
//        // Mock behavior when user is not found
//        when(customerUserRepository.findByEmail(anyString())).thenReturn(null);
//
//        // Call the method under test and expect UsernameNotFoundException
//        try {
//            userDetailsService.loadUserByUsername("nonexistent@example.com");
//            // Fail test if no exception is thrown
//            assert false;
//        } catch (UsernameNotFoundException e) {
//            // Assert the exception message
//            assertEquals("Invalid username or password.", e.getMessage());
//        }
//
//        // Verify repository method was called
//        verify(customerUserRepository, times(1)).findByEmail("nonexistent@example.com");
//    }
//
//
//
//    @Test
//    public void testSave() {
//        // Mock data
//        UserRegisteredDTO userDTO = new UserRegisteredDTO();
//        userDTO.setEmail_id("test@example.com");
//        userDTO.setName("Test User");
//        userDTO.setPassword("password123");
//        userDTO.setPhoneNumber("1234567890");
//        userDTO.setAddress("123 Test St, TestCity");
//
//        Role mockRole = new Role();
//        mockRole.setId(1);
//        mockRole.setRole("USER");
//
//        // Mock behavior of roleRepository.findByRole()
//        when(roleRepository.findByRole("USER")).thenReturn(mockRole);
//
//        // Mock behavior of passwordEncoder
//        //  when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
//
//        // Mock behavior of customerUserRepository.save()
//        when(customerUserRepository.save(any(CustomerUser.class))).thenAnswer(invocation -> {
//            CustomerUser user = invocation.getArgument(0);
//            user.setId(1); // Simulate setting an ID upon save
//            return user;
//        });
//
//        // Call method under test
//        CustomerUser savedUser = userDetailsService.save(userDTO);
//
//        // Verify roleRepository method was called
//        verify(roleRepository, times(1)).findByRole("USER");
//
//        // Verify passwordEncoder method was called with the expected argument
//        //  verify(passwordEncoder, times(1)).encode("password123");
//
//        // Verify customerUserRepository method was called
//        verify(customerUserRepository, times(1)).save(any(CustomerUser.class));
//
//        // Assert the returned user (optional, depending on your needs)
//        assertEquals("test@example.com", savedUser.getEmail());
//        assertEquals("Test User", savedUser.getName());
//        //assertEquals("encodedPassword", savedUser.getPassword()); // Ensure password is encoded
//        assertNotEquals(mockRole, savedUser.getRole()); // Ensure correct role is set
//        assertEquals("1234567890", savedUser.getPhoneNumber());
//        assertEquals("123 Test St, TestCity", savedUser.getAddress());
//    }
//}
