package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Model.LoginUser;
import com.example.SpringRestaurantswebsite.Repository.CustomerDetails;
import com.example.SpringRestaurantswebsite.Repository.LoginFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class CustomerDetailsService  implements UserDetailsService {

    @Autowired
    LoginFormRepository loginFormRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<LoginUser> loginUser = loginFormRepository.findUserByEmail(email);
        loginUser.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return loginUser.map(CustomerDetails::new).get();

    }
}
