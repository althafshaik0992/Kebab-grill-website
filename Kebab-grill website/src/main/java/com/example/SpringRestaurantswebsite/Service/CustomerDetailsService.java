package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public  interface CustomerDetailsService  extends UserDetailsService {




    CustomerUser save(UserRegisteredDTO userRegisteredDTO);



    boolean exists(String emailId);


}


