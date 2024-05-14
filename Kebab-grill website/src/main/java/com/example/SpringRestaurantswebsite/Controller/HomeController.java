package com.example.SpringRestaurantswebsite.Controller;



import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.MailServiceImpl;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;


@Controller
public class HomeController {


    @Autowired
    MailServiceImpl mailService;


    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProblemFormRepository problemFormRepository;




    @GetMapping({"/", "/home"})
    public String Home(Model model) {
        return "index";
    }


    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("products", productService.getProductList());
        return "menu";
    }

    @GetMapping("/menu/category/{id}")
    public String ViewCategoryMenu(Model model , @PathVariable int id) {
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("products", productService.getAllProductsByID(id));
        return "menu";
    }


    @GetMapping("/menu/viewmenu/{id}")
    public String ViewMenuItem(Model model ,@PathVariable int id) {
        model.addAttribute("products", productService.getProductById((long) id).get());
        return "viewItem";
    }








    @GetMapping("/reportForm")
    public String getRegPage(Model model) {
        model.addAttribute("problemForm", new ProblemForm());

        return "test";
    }

    @PostMapping("/reportForm")
    public String saveUser(@ModelAttribute("problemForm") ProblemForm user, Model model) throws Exception {
        if(problemFormRepository.existsByEmail(user.getEmail())){
            return String.valueOf(HttpStatus.BAD_REQUEST);
        }  else
        user.setPhoneNumber("+1-" +user.getPhoneNumber());
            problemFormRepository.save(user);
            UUID uuid = UUID.randomUUID();
//            mailService.send(user.getEmail(), "shaikguf15080@gmail.com"
//                    , "Thank you for  with us", "Hi" + user.getName() + "Thanks For Contacting Us" +
//                            "We will get back to you as soon as possible" + "please keep the reference id " + uuid + " for future use " +
//                            "Thank you " + "kebab Grill");
            model.addAttribute("message", "Submitted Successfully");
            return "test";

    }


    }


