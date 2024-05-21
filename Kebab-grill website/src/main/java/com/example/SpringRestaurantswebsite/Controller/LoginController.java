package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
    CustomerUserRepository customerUserRepository;
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    CustomerDetailsService customerDetailsService;

    @ModelAttribute("customer")
    public CustomerUserDto customerUserDto() {
        return new CustomerUserDto();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public void loginUser(@ModelAttribute("customer")
                          CustomerUserDto userLoginDTO) {
        System.out.println("UserDTO"+userLoginDTO);
        customerDetailsService.loadUserByUsername(userLoginDTO.getUsername());

    }






        @GetMapping("/dashboard")
        public String displayDashboard(Model model) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
                DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
                model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
            } else {
                User user = (User) securityContext.getAuthentication().getPrincipal();
                CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
                model.addAttribute("userDetails", users.getName());
            }
            return "index";
        }
    }




