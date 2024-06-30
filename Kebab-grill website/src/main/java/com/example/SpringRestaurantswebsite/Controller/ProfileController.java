package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;

import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.ProblemForm;

import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.mail.MessagingException;

@Controller
public class ProfileController {


    @Autowired
    CustomerUserRepository customerUserRepository;
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;


    @Autowired
    CustomerDetailsService customerDetailsService;


    @Autowired
    CustomerDetailsServiceImpl customerDetailsServiceImpl;






    @ModelAttribute("customer")
    public CustomerUserDto customerUserDto() {
        return new CustomerUserDto();
    }

    @ModelAttribute("contact")
    public CustomerUser customerUser() {
        return new CustomerUser();
    }





    @GetMapping("/profile")
    public String profile(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserRegisteredDTO userRegisteredDTO = new UserRegisteredDTO();
        if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            // userRegisteredDTO.setPhoneNumber("000-000-0000");
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
            model.addAttribute("userDetails1", user.getAttribute("email"));

            // model.addAttribute("userDetails2",userRegisteredDTO.getPhoneNumber());
        } else {
            User user = (User) securityContext.getAuthentication().getPrincipal();

            CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
            model.addAttribute("userDetails1", users.getEmail());
            model.addAttribute("userDetails2", users.getPhoneNumber());
            model.addAttribute("address",users.getAddress());

        }

        return "profilePage";
    }

//    @GetMapping("/admin/products/add")
//    public String updatePhoneNumber(Model model,CustomerUser user){
//        model.addAttribute("updateContact", user.getPhoneNumber());
//
//        return "p";
//    }




    @PostMapping("/updateAddress")
    public String updatePhoneNumber(CustomerUser user, Model model) {


        //    CustomerUser updatedUser = customerDetailsServiceImpl.getCustomerId(id).get();
//        CustomerUser customerUser = new CustomerUser();
//        customerUser.setPhoneNumber(updatedUser.getPhoneNumber());
//        customerUserRepository.save(customerUser);

        //    CustomerUser  contact = this.customerUserRepository.findById(id).get();

        user.setAddress(user.getAddress());


        customerUserRepository.save(user);
//        model.addAttribute("userDetails", user.getName());
//        model.addAttribute("userDetails", user.getEmail());
        model.addAttribute("contact", user);
        model.addAttribute("message", "Submitted Successfully");
        return "redirect:/profilePage";
    }

//    @GetMapping("/addressform")
//    public String getRegPage(Model model) {
//        model.addAttribute("problemForm", new AddressForm());
//
//        return "addresspage";
//    }
//
//    @PostMapping("/addressform")
//    public String saveUser( AddressForm user, Model model) {
//
//       addressFormRepository.save(user);
//        model.addAttribute("user",customerUser().getName());
//        model.addAttribute("message", "Submitted Successfully");
//        return "addresspage";
//    }
}
