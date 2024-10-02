package com.example.SpringRestaurantswebsite.Repository;

import com.example.SpringRestaurantswebsite.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {


    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> findAllByCategoryId(int id);

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);



    @Query(value = "select " +
            "p.id, p.name, p.description, p.quantity, p.price, p.category_id, p.image_Name, p.is_activated, p.is_deleted " +
            "from product p where p.is_deleted = false and p.is_activated = true order by p.price desc limit 9", nativeQuery = true)
    List<Product> filterHighProducts();

    @Query(value = "select " +
            "p.id, p.name, p.description, p.quantity, p.price, p.category_id, p.image_Name, p.is_activated, p.is_deleted " +
            "from product p where p.is_deleted = false and p.is_activated = true order by p.price asc limit 9", nativeQuery = true)
    List<Product> filterLowerProducts();

    @Query(value = "select " +
            "p.id, p.name, p.description, p.quantity, p.price, p.category_id, p.image_Name, p.is_activated, p.is_deleted " +
            "from product p where p.is_deleted = false and p.is_activated = true  limit 4", nativeQuery = true)
    List<Product> listViewProduct();


    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProducts(String keyword);

}
