package com.example.SpringRestaurantswebsite.Controller;


    import com.example.SpringRestaurantswebsite.Dto.ProductDto;
    import com.example.SpringRestaurantswebsite.Model.Category;
    import com.example.SpringRestaurantswebsite.Model.Product;
    import com.example.SpringRestaurantswebsite.Service.CategoryService;
    import com.example.SpringRestaurantswebsite.Service.ProductService;
    import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
    import org.springframework.mock.web.MockMultipartFile;
    import org.springframework.ui.Model;

    import java.io.IOException;
    import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

        @InjectMocks
        private AdminController adminController;

        @Mock
        private CategoryService categoryService;

        @Mock
        private ProductService productService;

        @Mock
        private Model model;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testCategoriesList() {
            // Mock data
            List<Category> mockCategories = Arrays.asList(
                    new Category(1, "Category 1"),
                    new Category(2, "Category 2")
            );
            when(categoryService.getCategoryList()).thenReturn(mockCategories);

            // Call controller method
            String viewName = adminController.categories(model);

            // Verify model attributes
            verify(model).addAttribute("categories", mockCategories);
            verifyNoMoreInteractions(model);

            // Assert view name returned
            assertEquals("categories", viewName);
        }

        @Test
        public void testProductList() {
            // Mock data
            List<Product> mockProducts = Arrays.asList(
                    new Product(1L, "Product 1", 100.0, new Category(1, "Category 1").getId()),
                    new Product(2L, "Product 2", 200.0, new Category(2, "Category 2").getId())
            );
            when(productService.getProductList()).thenReturn(mockProducts);

            // Call controller method
            String viewName = adminController.products(model);

            // Verify model attributes
            verify(model).addAttribute("products", mockProducts);
            verifyNoMoreInteractions(model);

            // Assert view name returned
            assertEquals("product", viewName);
        }


    @Test
    public void testGetcategoriesAdd() {
        // Call controller method
        String viewName = adminController.getcategoriesAdd(model);

        // Verify model attributes
        verify(model).addAttribute(eq("category"), any(Category.class));
        verifyNoMoreInteractions(model);

        // Assert view name returned
        assertEquals("categoriesAdd", viewName);
    }


    @Test
    public void testPostcategoriesAdd() {
        // Mock category object and service behavior
        Category mockCategory = new Category();
        mockCategory.setId(1); // Assuming some ID is set after adding

        //when(categoryService.addCategory(any(Category.class))).thenReturn(mockCategory);

        // Call controller method
        String viewName = adminController.postcategoriesAdd(mockCategory);

        // Verify categoryService.addCategory was called with the correct category object
        verify(categoryService).addCategory(mockCategory);
        verifyNoMoreInteractions(categoryService);

        // Assert redirect URL returned
        assertEquals("redirect:/admin/categories", viewName);
    }

    @Test
    public void testCatDeleteById() {
        // Mock category ID
        int categoryIdToDelete = 1;

        // Call controller method
        String viewName = adminController.deleteById(categoryIdToDelete);

        // Verify categoryService.deleteById was called with the correct ID
        verify(categoryService).deleteById(categoryIdToDelete);
        verifyNoMoreInteractions(categoryService);

        // Assert redirect URL returned
        assertEquals("redirect:/admin/categories", viewName);
    }


    @Test
    public void testUpdateById_CategoryExists() {
        // Mock category data
        int categoryId = 1;
        Category mockCategory = new Category(categoryId, "Category Name");

        // Mock service behavior
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(mockCategory));

        // Call controller method
        String viewName = adminController.updateById(categoryId, model);

        // Verify categoryService.getCategoryById was called with the correct ID
        verify(categoryService).getCategoryById(categoryId);
        verify(model).addAttribute("category", mockCategory);
        verifyNoMoreInteractions(categoryService, model);

        // Assert view name returned
        assertEquals("categoriesAdd", viewName);
    }

    @Test
    public void testUpdateById_CategoryNotExists() {
        // Mock category data
        int categoryId = 999; // Non-existing category ID

        // Mock service behavior
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        // Call controller method
        String viewName = adminController.updateById(categoryId, model);

        // Verify categoryService.getCategoryById was called with the correct ID
        verify(categoryService).getCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);

        // Assert view name returned
        assertEquals("404 Not Found ", viewName); // Ensure there is a space after "404 Not Found"
    }


    @Test
    public void testGetProductAdd() {
        // Mock category data
        List<Category> mockCategories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2")
        );

        // Mock service behavior
        when(categoryService.getCategoryList()).thenReturn(mockCategories);

        // Call controller method
        String viewName = adminController.getProductAdd(model);

        // Verify categoryService.getCategoryList was called
        verify(categoryService).getCategoryList();

        // Verify model attributes
        verify(model).addAttribute("productDto", new ProductDto());
        verify(model).addAttribute("categories", mockCategories);
        verifyNoMoreInteractions(categoryService, model);

        // Assert view name returned
        assertEquals("productAdd", viewName);
    }

//    @Test
//    public void testPostProductAdd() throws IOException {
//        // Mock productDto data
//        ProductDto productDto = new ProductDto();
//        productDto.setName("Product Name");
//        productDto.setCategoryId(1); // Assuming category ID
//        productDto.setPrice(100.0);
//        productDto.setDescription("Product Description");
//
//        // Mock category data
//        Category mockCategory = new Category();
//        mockCategory.setId(productDto.getCategoryId());
//        mockCategory.setName("Category Name");
//
//        // Mock service behavior for getCategoryById
//        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.of(mockCategory));
//
//        // Mock MultipartFile
//        MockMultipartFile mockFile = new MockMultipartFile("productImage", "food 2.jpeg", "image/jpeg", "test image content".getBytes());
//
//        // Call controller method
//        String viewName = adminController.postProductAdd(productDto, mockFile,"default-image.jpg");
//
//        // Verify categoryService.getCategoryById was called with the correct category ID
//        verify(categoryService).getCategoryById(productDto.getCategoryId());
//
//        // Verify productService.addProduct was called with the correct Product object
//        verify(productService).addProduct(any(Product.class));
//        verifyNoMoreInteractions(categoryService, productService);
//
//        // Assert redirect URL returned
//        assertEquals("redirect:/admin/products", viewName);
//    }


    @Test
    public void testDeleteById() {
        // Mock product ID
        Long productId = 1L;

        // Call controller method
        String viewName = adminController.deleteById(productId);

        // Verify productService.deleteById was called with the correct ID
        verify(productService).deleteById(productId);
        verifyNoMoreInteractions(productService);

        // Assert redirect URL returned
        assertEquals("redirect:/admin/products", viewName);
    }

    @Test
    public void testUpdateById_ProductExists() {
        // Mock product data
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Product Name");
        Category mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setName("Category Name");
        mockProduct.setCategory(mockCategory);
        mockProduct.setPrice(100.0);
        mockProduct.setDescription("Product Description");
        mockProduct.setImageName("product-image.jpg");

        // Mock service behavior for getProductById
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(mockProduct));

        // Mock service behavior for getCategoryList
        List<Category> mockCategories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2")
        );
        when(categoryService.getCategoryList()).thenReturn(mockCategories);

        // Call controller method
        String viewName = adminController.updateById(productId, model);

        // Verify productService.getProductById was called with the correct ID
        verify(productService).getProductById(productId);

        // Verify model attributes
        verify(model).addAttribute(eq("categories"), eq(mockCategories));
        verify(model).addAttribute(eq("productDto"), any(ProductDto.class));
        verifyNoMoreInteractions(productService, model);

        // Assert view name returned
        assertEquals("productAdd", viewName);
    }

    @Test
    public void testUpdateById_ProductNotExists() {
        // Mock product ID for a non-existing product
        Long productId = 999L;

        // Mock service behavior for getProductById
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        // Call controller method
        String viewName = adminController.updateById(productId, model);

        // Verify productService.getProductById was called with the correct ID
        verify(productService).getProductById(productId);

        // Verify no model attributes were added
        verify(model, never()).addAttribute(anyString(), any());

        // Assert view name returned
        assertEquals("404 Not Found ", viewName.trim()); // Ensure there is a space after "404 Not Found"
    }

    // Add more test cases for other methods as needed

}

    // Add more test cases for other methods as needed




