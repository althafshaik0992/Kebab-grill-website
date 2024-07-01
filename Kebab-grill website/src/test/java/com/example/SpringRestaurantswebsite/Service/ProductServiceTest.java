package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;

    private ProductService productService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(mockProductRepository);
    }

    @Test
    public void testGetProductList() {
        // Mock data
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "Product A", 1));
        mockProducts.add(new Product(2, "Product B", 1));

        // Mock repository method
        when(mockProductRepository.findAll()).thenReturn(mockProducts);

        // Call getProductList method
        List<Product> result = productService.getProductList();

        // Verify the result
        assertEquals(mockProducts, result);

        // Verify repository method was called
        verify(mockProductRepository, times(1)).findAll();
    }

    @Test
    public void testAddProduct() {
        // Mock product
        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setPrice(100.0);

        // Call addProduct method
        productService.addProduct(product);

        // Verify that save() method was called on the repository with the correct product
        verify(mockProductRepository, times(1)).save(product);
    }


    @Test
    public void testDeleteById() {
        // Mock product ID
        Long productId = 1L;

        // Call deleteById method
        productService.deleteById(productId);

        // Verify that deleteById() method was called on the repository with the correct ID
        verify(mockProductRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testGetProductById() {
        // Mock product data
        Integer productId = 1;
        Product mockProduct = new Product(productId, "Product A", 10);

        // Mock repository method
        when(mockProductRepository.findById(Long.valueOf(productId))).thenReturn(Optional.of(mockProduct));

        // Call getProductById method
        Optional<Product> result = productService.getProductById(Long.valueOf(productId));

        // Verify the result
        assertEquals(Optional.of(mockProduct), result);

        // Verify repository method was called
        verify(mockProductRepository, times(1)).findById(Long.valueOf(productId));
    }


    @Test
    public void testGetAllProductsByID() {
        // Mock category ID
        int categoryId = 1;

        // Mock product data
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product A", 100.0, categoryId));
        mockProducts.add(new Product(2L, "Product B", 150.0, categoryId));

        // Mock repository method
        when(mockProductRepository.findAllByCategoryId(categoryId)).thenReturn(mockProducts);

        // Call getAllProductsByID method
        List<Product> result = productService.getAllProductsByID(categoryId);

        // Verify the result
        assertEquals(mockProducts.size(), result.size());
        assertEquals(mockProducts.get(0).getId(), result.get(0).getId());
        assertEquals(mockProducts.get(1).getName(), result.get(1).getName());

        // Verify repository method was called
        verify(mockProductRepository, times(1)).findAllByCategoryId(categoryId);
    }
}
