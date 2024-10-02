package com.example.SpringRestaurantswebsite.Controller;


import com.example.SpringRestaurantswebsite.Global.GlobalData;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {



    @Autowired
    ProductService productService;

    private Map<Long, Integer> cart = new HashMap<>();

    @GetMapping("/add/cart/{id}")
    public String cartModel(@PathVariable Long id ,Model model) {

       GlobalData.cart.add(productService.getProductById(id).get());

//        if (cart.containsKey(id)) {
//            cart.put(id, cart.get(id) + 1);
//        }else {
//            cart.put(id, 1);
//        }
//        model.addAttribute("cart", cart);

        return "redirect:/menu";
    }


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("product_id") Long productId, Model model) {
        // Add product to the cart
        GlobalData.cart.add(productService.getProductById(productId).get());

        // Optionally, update the cart model
        model.addAttribute("cart", GlobalData.cart);

        return "redirect:/menu";
    }


    @PostMapping("/add-to-cart-login")
    public String addToCartLogin(@RequestParam("product_id") Long productId, Model model) {
        // Add product to the cart
        GlobalData.cart.add(productService.getProductById(productId).get());

        // Optionally, update the cart model
        model.addAttribute("cart", GlobalData.cart);

        return "redirect:/dashboard";
    }
//    @PostMapping("/add/cart/{id}")
//    public String cart(Model model, @PathVariable Long id)  {
//        GlobalData.cart.add(productService.getProductById(id).get());
//        model.addAttribute("cart", GlobalData.cart);
//        model.addAttribute("cartCount", GlobalData.cart.size());
//
//        return "redirect:/menu";
//    }


    @GetMapping("/cart")
    public String cartModel(Model model) {


//


            model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }


    @GetMapping("/cart/removeItem/{index}")
    public String cartRemoveItem(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
        public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
    }





