package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public  interface CustomerDetailsService  extends UserDetailsService {




    CustomerUser save(UserRegisteredDTO userRegisteredDTO);



    boolean exists(String emailId);
}


