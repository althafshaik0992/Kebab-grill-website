package com.example.SpringRestaurantswebsite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.boot.SpringApplication;

import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class SpringRestaurantsWebisteApplicationTests {

    @Test
    public void testApplication() {
        MockedStatic<SpringApplication> utilities = Mockito.mockStatic(SpringApplication.class);
        utilities.when((MockedStatic.Verification) SpringApplication.run(SpringRestaurantsWebsiteApplication.class, new String[]{})).thenReturn(null);
        SpringRestaurantsWebsiteApplication.main(new String[]{});
        assertThat(SpringApplication.run(SpringRestaurantsWebsiteApplication.class, new String[]{})).isEqualTo(null);
    }
//@Test
//public void testMainMethod() {
//    SpringApplication springApplicationMock = Mockito.mock(SpringApplication.class);
//    String[] args = {"arg1", "arg2"}; // Example arguments
//
//    // Call the main method
//    SpringRestaurantsWebsiteApplication.main(args);
//
//    // Verify that SpringApplication.run was called with the correct class and arguments
//    verify(springApplicationMock).run(SpringRestaurantsWebsiteApplication.class, args);
//}










    }


