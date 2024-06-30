package Service;

import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Repository.CategoryRepository;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;



    @Test
    public void testGetCategoryList() {
        // Mock data
        List<Category> mockCategories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2")
        );

        // Mocking behavior of categoryRepository.findAll()
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        // Call method under test
        List<Category> result = categoryService.getCategoryList();

        // Debug output to inspect result and mockCategories
        System.out.println("Mock Categories: " + mockCategories);
        System.out.println("Result: " + result);

        // Verify the result
        assertEquals(mockCategories.size(), result.size(), "List sizes do not match");

        // Verify each category individually
        for (int i = 0; i < mockCategories.size(); i++) {
            assertEquals(mockCategories.get(i).getId(), result.get(i).getId(), "Category id does not match at index " + i);
            assertEquals(mockCategories.get(i).getName(), result.get(i).getName(), "Category name does not match at index " + i);
        }

        // Verify repository method was called
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        // Mock data
        int categoryIdToDelete = 1;

        // Call method under test
        categoryService.deleteById(categoryIdToDelete);

        // Verify repository method was called with correct argument
        verify(categoryRepository, times(1)).deleteById(categoryIdToDelete);
    }




}