package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @Mock
    private ProblemFormRepository problemFormRepository;

    @Mock
    private Session session;

    @Mock
    private Transport transport;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser_Successful() throws Exception {
        // Mock data
        ProblemForm problemForm = new ProblemForm();
        problemForm.setEmail("test@example.com");
        problemForm.setName("Test User");
        problemForm.setPhoneNumber("1234567890");

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);

        // Mock repository save method
        when(problemFormRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(problemFormRepository.save(any(ProblemForm.class))).thenReturn(problemForm);

        // Mock email sending
        MimeMessage mimeMessage = new MimeMessage(session);
        doNothing().when(transport).send(mimeMessage);

        // Test
        String viewName = homeController.saveUser(problemForm, model);

        assertEquals("reportForm", viewName); // Should return to reportForm page
        verify(problemFormRepository, times(1)).save(problemForm); // Verify save method called once
        verify(model, times(1)).addAttribute(eq("message"), eq("Submitted Successfully")); // Verify message attribute set
    }

    @Test
    public void testSaveUser_EmailExists() throws Exception {
        // Mock data
        ProblemForm problemForm = new ProblemForm();
        problemForm.setEmail("existing@example.com");

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);

        // Mock repository exists method
        when(problemFormRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Test
        String viewName = homeController.saveUser(problemForm, model);

        assertEquals("400 BAD_REQUEST", viewName); // Should return "400" for bad request
        verify(problemFormRepository, never()).save(any(ProblemForm.class)); // Ensure save method is never called
    }



}
