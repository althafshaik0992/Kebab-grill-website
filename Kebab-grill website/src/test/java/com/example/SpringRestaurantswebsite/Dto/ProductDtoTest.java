package com.example.SpringRestaurantswebsite.Dto;

import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Model.Product;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest {



        @Test
        public void testNameField() {
            ProductDto product = new ProductDto();

            Long id= 1L;
            String expectedName = "Test Product";
            int  expectedCategoryId = 1;
            double expectedPrice = 99.0;
            String expectedDescription = "This is a test product.";
            String expectedImageName = "product_image.jpg";


            product.setId(id);
            product.setName(expectedName);
            product.setCategoryId(expectedCategoryId);
            product.setPrice(expectedPrice);
            product.setDescription(expectedDescription);
            product.setImageName(expectedImageName);


            assertEquals(id, product.getId());
            assertEquals(expectedName, product.getName());
           assertEquals(expectedImageName, product.getImageName());
            assertEquals(expectedDescription, product.getDescription());
         //   assertEquals(expectedPrice, product.getPrice());
            assertEquals(expectedCategoryId, product.getCategoryId());

        }
    }


