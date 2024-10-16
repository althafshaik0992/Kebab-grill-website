package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.ProductDto;
import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Global.GlobalData;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.PasswordResetToken;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Repository.TokenRepository;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
import com.example.SpringRestaurantswebsite.Service.CustomerDetailsServiceImpl;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import jakarta.servlet.http.HttpSession;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {




    @Autowired
    CustomerUserRepository customerUserRepository;
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;



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
    public String login(Model model) {
        GlobalData.cart.clear();
        return "login";
    }


    @GetMapping("/loginguest")
    public String guest(Model model) {

        CustomerUser user = new CustomerUser();
        user.setName("guest");

        model.addAttribute("userDetails", user.getName());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getProductList());
        return "loginguest";
    }


    @GetMapping("/loginafter")
    public String loginafter(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = (User) securityContext.getAuthentication().getPrincipal();
        CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
        model.addAttribute("userDetails", users.getName());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getProductList());

        return "loginafterPage";
    }

    @PostMapping("/login")
    public void loginUser(@ModelAttribute("customer")
                          CustomerUserDto userLoginDTO , CustomerUser user) {
        System.out.println("UserDTO"+userLoginDTO);
        customerDetailsService.loadUserByUsername(userLoginDTO.getUsername());

    }






//        @GetMapping("/dashboard")
//        public String displayDashboard(Model model, Principal principal, HttpSession session) {
//            if (principal != null) {
//                // Check if the cart is stored in session
//                if (session.getAttribute("cart") != null) {
//                    GlobalData.cart = (List<Product>) session.getAttribute("cart");
//                } else {
//                    // If there's no cart in the session, initialize it
//                    GlobalData.cart = new ArrayList<>(); // Or however you want to initialize it
//                }
//            }
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
//                DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
//                model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
//                model.addAttribute("categories", categoryService.getCategoryList());
//                model.addAttribute("cartCount", GlobalData.cart.size());
//                model.addAttribute("products", productService.getProductList());
//
//
//            } else {
//                User user = (User) securityContext.getAuthentication().getPrincipal();
//                CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
//                model.addAttribute("userDetails", users.getName());
//                model.addAttribute("categories", categoryService.getCategoryList());
//                model.addAttribute("cartCount", GlobalData.cart.size());
//                model.addAttribute("products", productService.getProductList());
//
//            }
//            model.addAttribute("cartCount", GlobalData.cart.size());
//            model.addAttribute("cart", GlobalData.cart);
//            return "loginafterPage";
//
//        }




    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Principal principal, HttpSession session) {
        // Retrieve the cart from session if the user is logged in
        if (principal != null) {
            // Check if the cart is stored in session
            if (session.getAttribute("cart") != null) {
                GlobalData.cart = (List<Product>) session.getAttribute("cart");
            } else {
                // If there's no cart in the session, initialize it
                GlobalData.cart = new ArrayList<>(); // Or however you want to initialize it
            }
        }

        // Security context to get user details
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
        } else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
        }

        // Prepare other model attributes
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size()); // Count of items in the cart
        model.addAttribute("products", productService.getProductList());
        model.addAttribute("cart", GlobalData.cart); // Pass the cart to the view

        return "loginafterPage"; // Render the dashboard page
    }


    @GetMapping("/menuafterlogin")
    public String loginafterlogin(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
            model.addAttribute("categories", categoryService.getCategoryList());
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("products", productService.getProductList());

        } else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
            model.addAttribute("categories", categoryService.getCategoryList());
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("products", productService.getProductList());


        }
        return "menuafterlogin";
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




