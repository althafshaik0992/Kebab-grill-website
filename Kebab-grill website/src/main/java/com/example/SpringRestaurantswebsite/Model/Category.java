package com.example.SpringRestaurantswebsite.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Category {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "category_id")
    private int  id ;

    private String name ;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public int getProductCount() {
        return products.size();
    }

    public Category(int i, String s) {
    }

    public Category(){

    }
}
