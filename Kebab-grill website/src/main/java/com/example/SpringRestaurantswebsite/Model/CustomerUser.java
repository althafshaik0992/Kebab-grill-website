package com.example.SpringRestaurantswebsite.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
public class CustomerUser {






        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        private String name;

        private String email;

        private String password;

        private String phoneNumber;

        private String address;



        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "cust_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
        Set<Role> role = new HashSet<Role>();

    public CustomerUser() {

    }

    public CustomerUser(String email, String password, Set<Role> role) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Set<Role> getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role.add(role);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}

