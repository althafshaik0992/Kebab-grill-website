package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Repository.CategoryRepository;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

//
//    private final CategoryRepository categoryRepository;
//
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProblemFormRepository problemFormRepository;




    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    public void addCategory( Category category){

        categoryRepository.save(category);
    }


    public void deleteById(int   id ){
        categoryRepository.deleteById(id);
    }

    public Optional< Category> getCategoryById(int  id ){
        return categoryRepository.findById(id);
    }


    public void addproblem(ProblemForm problemForm){
        problemFormRepository.save(problemForm);

    }



}
