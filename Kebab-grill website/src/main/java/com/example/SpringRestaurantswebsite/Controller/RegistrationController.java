package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {


    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    CustomerDetailsService customerDetailsService;

    @Autowired
    CustomerUserRepository customerUserRepository;


    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto() {
        return new UserRegisteredDTO();
    }

    @GetMapping("/registration")
    public String register() {
        return "register";
    }


    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid
                                      UserRegisteredDTO registrationDto, BindingResult bindingResult , RedirectAttributes redirectAttributes) {
       if (customerDetailsService.exists(registrationDto.getEmail_id())==true) {
           bindingResult.addError( new FieldError("registrationDto","email_id","Email already in use "));
           redirectAttributes.addFlashAttribute("message","Email Already In use");
           return "register";
        }
         else {

           customerDetailsService.save(registrationDto);
           redirectAttributes.addFlashAttribute("message","Your Account Registered Successfully ");
           return "redirect:/login";
       }
//        model.addAttribute("message", "Registered  Successfully");

    }




}

