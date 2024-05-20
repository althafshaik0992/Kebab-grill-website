package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {


    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    CustomerDetailsService customerDetailsService;




    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto() {
        return new UserRegisteredDTO();
    }

    @GetMapping("/registration")
    public String register() {
        return "register";
    }



    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegisteredDTO registrationDto ) {
        customerDetailsService.save(registrationDto);
//        model.addAttribute("message", "Registered  Successfully");
        return "redirect:/login";
    }


}
