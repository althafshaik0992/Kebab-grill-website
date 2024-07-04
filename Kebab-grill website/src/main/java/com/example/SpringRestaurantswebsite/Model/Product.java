package com.example.SpringRestaurantswebsite.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String name;



    @ManyToOne(fetch= FetchType.LAZY )
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;

    private double price;

private  int quantity;

    private String  description   ;

    private String imageName;


    public Product(int i, String s, int ten) {
    }

    public Product(){

    }


    public Product(long l, String productA, double v, int categoryId) {
    }
}
