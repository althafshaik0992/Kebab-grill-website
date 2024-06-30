package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.ProductDto;
import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.PasswordResetToken;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Repository.TokenRepository;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsServiceImpl;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {




    @Autowired
    CustomerUserRepository customerUserRepository;
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;



    @Autowired
    CustomerDetailsService customerDetailsService;


    @Autowired
    CustomerDetailsServiceImpl customerDetailsServiceImpl;



    @Autowired
    TokenRepository tokenRepository;

    @ModelAttribute("customer")
    public CustomerUserDto customerUserDto() {
        return new CustomerUserDto();
    }

    @ModelAttribute("contact")
    public CustomerUser customerUser() {
        return new CustomerUser();
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
            return "loginafterPage";
        }





    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassordProcess(@ModelAttribute CustomerUser userDTO) {
        String output = "";

        CustomerUser user = customerUserRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            output =customerDetailsServiceImpl.sendEmail(user);
        }
        if (output.equals("success")) {
            return "redirect:/forgotPassword?success";
        }
        return "";
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && customerDetailsServiceImpl.hasExipred(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute CustomerUser userDTO) {
        CustomerUser user = customerUserRepository.findByEmail(userDTO.getEmail());
        if( user!= null) {
            user.setPassword(cryptPasswordEncoder.encode(userDTO.getPassword()));
            System.out.println("the updated user is  "  +user);
           customerUserRepository.save(user);

        }
        return "redirect:/login";
    }






    }




