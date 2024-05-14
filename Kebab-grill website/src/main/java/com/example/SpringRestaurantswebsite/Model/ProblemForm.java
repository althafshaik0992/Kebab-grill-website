package com.example.SpringRestaurantswebsite.Model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="report_fprm")
public class ProblemForm {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone _number")
    private String phoneNumber;


    @Column(name = "comments")
    private String Comments;





}

