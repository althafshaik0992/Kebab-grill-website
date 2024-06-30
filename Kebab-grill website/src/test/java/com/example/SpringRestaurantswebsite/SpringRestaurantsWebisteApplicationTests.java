package com.example.SpringRestaurantswebsite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.boot.SpringApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class SpringRestaurantsWebisteApplicationTests {

    @Test
    public void testMainMethod() {
        // Mock SpringApplication.run() to verify its invocation
        SpringApplication springApplicationMock = Mockito.mock(SpringApplication.class);

        // Call the main method
        String[] args = {}; // Example arguments
        SpringRestaurantsWebsiteApplication.main(args);

        // Verify that SpringApplication.run() was called with the correct class and arguments
        verify(springApplicationMock, times(1)).run(SpringRestaurantsWebsiteApplication.class, args);
    }
}
