package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public  interface CustomerDetailsService  extends UserDetailsService  {


    CustomerUser save(UserRegisteredDTO userRegisteredDTO);


}
