package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Global.GlobalData;
import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;

import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest {

    @Mock
    private ProblemFormRepository problemFormRepository;

    @Mock
    private Session session;

    @Mock
    private Transport transport;

    @InjectMocks
    private HomeController homeController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private HomeController menuController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);
       this.mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }




//    @Test
//    public void testRegister() {
//       Model model = null;
//        String viewName = homeController.Home( model);
//        model.addAttribute("cartCount", GlobalData.cart.size());
//        assertEquals("index", viewName);
//        // Add more assertions if necessary, e.g., model attributes being set
//    }



    @Test
    public void testMenu() throws Exception {
        // Mock data
        List<Category> mockCategories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2")
        );
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "Product 1", 1),
                new Product(2, "Product 2", 1)
        );

        // Mock behavior of categoryService.getCategoryList() and productService.getProductList()
        when(categoryService.getCategoryList()).thenReturn(mockCategories);
        when(productService.getProductList()).thenReturn(mockProducts);

        // Perform the GET request
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andExpect(model().attributeExists("categories", "products"))
                .andExpect(model().attribute("categories", mockCategories))
                .andExpect(model().attribute("products", mockProducts));
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
