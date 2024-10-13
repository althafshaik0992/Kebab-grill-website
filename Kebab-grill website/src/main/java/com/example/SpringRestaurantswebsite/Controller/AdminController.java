package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Dto.ProductDto;
import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

@Controller
public class AdminController {

    public static  String uploadDrive = System.getProperty("user.dir") + "/Kebab-grill website/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;

     @Autowired
    ProductService productService;


    @GetMapping("/admin")
    public String admin(){
        return "Admin";
    }
    //categories section
    @GetMapping("/admin/categories")
    public String categories(Model model){
        model.addAttribute("categories",categoryService.getCategoryList());
        return "categories";
    }



    @GetMapping("/admin/categories/add")
    public String getcategoriesAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }


    @PostMapping("/admin/categories/add")
    public String postcategoriesAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/product/enable/{id}")
    public String enabledProduct(@PathVariable  Long id, RedirectAttributes redirectAttributes) {
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        return "redirect:/admin/products";
    }


    @GetMapping("/admin/categories/delete/{id}")
    public String  deleteById(@PathVariable int  id ){

        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }


    @GetMapping("/admin/categories/update/{id}")
    public String  updateById(@PathVariable int id  , Model model){

       Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());

            return "categoriesAdd";
        } else
        return "404 Not Found ";
    }



   // product section


    @GetMapping("admin/products")
    public String products(Model model){
        model.addAttribute("products", productService.getProductList());
        return "product";
    }


    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories",categoryService.getCategoryList());
        return "productAdd";
    }


    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("productDto") ProductDto productDto ,
                                @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
      Product product = new Product();
      product.setId(product.getId());
      product.setName(productDto.getName());
   //     System.out.println(" the value is "  + categoryService.getCategoryById(productDto.getCategoryId()).get());
     product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()).get());
      product.setPrice(productDto.getPrice());
      product.setDescription(productDto.getDescription());
      String imageUUID;
        if(!file.isEmpty()) {
            imageUUID =file.getOriginalFilename();
            Path filegetpath = Paths.get(uploadDrive,imageUUID);
            Files.write(filegetpath, file.getBytes());
        }else {
           imageUUID = imgName;
        }
        product.setImageName(imageUUID);
      productService.addProduct(product);
        return "redirect:/admin/products";
    }




    @GetMapping("/admin/product/delete/{id}")
    public String  deleteById(@PathVariable Long  id ){
       productService.deleteById(id);
        return "redirect:/admin/products";
    }




    @GetMapping("/admin/product/update/{id}")
    public String  updateById(@PathVariable Long id , Model model){
       Product product = productService.getProductById(id).get();
       ProductDto productDto = new ProductDto();
       productDto.setId(product.getId());
       productDto.setName(product.getName());
       productDto.setCategoryId(product.getCategory().getId());
       productDto.setPrice(product.getPrice());
       productDto.setDescription(product.getDescription());
       productDto.setImageName(product.getImageName());

        model.addAttribute("categories",categoryService.getCategoryList());
        model.addAttribute("productDto", productDto);

        return "productAdd";

    }









}
