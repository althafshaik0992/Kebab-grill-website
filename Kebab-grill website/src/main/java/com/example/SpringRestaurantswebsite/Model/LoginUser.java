package com.example.SpringRestaurantswebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "login_user")
public class LoginUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @NotEmpty
    @Column(nullable = false)
    private String firstName;


    private String lastName;


    @NotEmpty
    @Column(nullable = false)
    @Email(message ="{errors.invalidEmail}")
    private String email;

    @NotEmpty
    private  String password;


     @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
     @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "ID"),
             inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "ID"))
     private List<Roles> roles;


    public LoginUser(LoginUser loginUser) {
        this.id = loginUser.getId();
        this.firstName = loginUser.getFirstName();
        this.lastName = loginUser.getLastName();
        this.email = loginUser.getEmail();
        this.password = loginUser.getPassword();
        this.roles = loginUser.getRoles();
    }

    public LoginUser() {
        
    }
}
