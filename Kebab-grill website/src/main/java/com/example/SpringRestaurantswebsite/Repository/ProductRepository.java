package com.example.SpringRestaurantswebsite.Repository;

import com.example.SpringRestaurantswebsite.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryId(int id);



}
