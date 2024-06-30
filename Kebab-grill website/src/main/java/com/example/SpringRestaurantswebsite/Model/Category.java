package com.example.SpringRestaurantswebsite.Model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Category {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "category_id")
    private int  id ;

    private String name ;

    public Category(int i, String s) {
    }

    public Category(){

    }
}
