package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    CustomerDetailsService customerDetailsService;

    @ModelAttribute("user")
    public CustomerUserDto customerUserDto() {
        return new CustomerUserDto();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping
    public void loginUser(@ModelAttribute("user")
                          CustomerUserDto userLoginDTO) {
        System.out.println("UserDTO"+userLoginDTO);
        customerDetailsService.loadUserByUsername(userLoginDTO.getUsername());
    }



}
