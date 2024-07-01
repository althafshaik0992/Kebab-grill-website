package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;



@Component
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public void addProduct( Product product){

       productRepository.save(product);
    }


    public void deleteById(Long  id ){
        productRepository.deleteById(id);
    }

    public Optional< Product> getProductById(Long id ){
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByID(int id){

        return productRepository.findAllByCategoryId(id);
    }
}
