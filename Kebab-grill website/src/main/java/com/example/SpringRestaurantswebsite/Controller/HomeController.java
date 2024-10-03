package com.example.SpringRestaurantswebsite.Controller;




import com.example.SpringRestaurantswebsite.Dto.CustomerUserDto;
import com.example.SpringRestaurantswebsite.Dto.ProductDto;
import com.example.SpringRestaurantswebsite.Global.GlobalData;
import com.example.SpringRestaurantswebsite.Model.Category;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Model.Product;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import com.google.zxing.EncodeHintType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


@Controller
public class HomeController {


    @Value("${spring.mail.username}")
    private String username;

    private final String fromEmail = "shaikguf15080@gmail.com";
//
//    private final String toEmaile = "sk.murfu@gmail.com";


    @Value("${spring.mail.password}")
    private String password;

//    @Autowired
//    MailNotification mailNotification;


    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProblemFormRepository problemFormRepository;

    @Autowired
    CustomerUserRepository customerUserRepository;


    @ModelAttribute("customer")
    public CustomerUserDto customerUserDto() {
        return new CustomerUserDto();
    }

    @GetMapping({"/", "/home"})
    public String Home(Model model) {
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getProductList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }


    @GetMapping("/menu")
    public String menu(Model model) {

        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("products", productService.getProductList());
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "menu";
    }


    @GetMapping("/menuguest")
    public String menuguest(Model model) {

        CustomerUser user = new CustomerUser();
        user.setName("Guest");

        model.addAttribute("userDetails", user.getName());
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getProductList());
        return "menuguestlogin";
    }

//

    @GetMapping("/menu/category/{id}")
    public String ViewCategoryMenu(Model model , @PathVariable int id) {

        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProductsByID(id));
        return "menu";
    }

    @GetMapping("/menuafter/category/{id}")
    public String ViewCategoryMenuafter(Model model , @PathVariable int id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
            model.addAttribute("categories", categoryService.getCategoryList());
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("products", productService.getAllProductsByID(id));

        } else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("categories", categoryService.getCategoryList());
            model.addAttribute("products", productService.getAllProductsByID(id));
        }
        return "menuafterlogin";
    }


    @GetMapping("/menuguest/category/{id}")
    public String ViewCategoryMenuguest(Model model , @PathVariable int id) {
        CustomerUser user = new CustomerUser();
        user.setName("Guest");

        model.addAttribute("userDetails", user.getName());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("products", productService.getAllProductsByID(id));
        return "menuguestlogin";
    }


    @GetMapping("/menu/viewmenu/{id}")
    public String ViewMenuItem(Model model ,@PathVariable int id) {
        model.addAttribute("products", productService.getProductById((long) id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "viewItem";
    }

    @GetMapping("/menuafter/viewmenu/{id}")
    public String ViewMenuItemafterlogin(Model model ,@PathVariable int id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("products", productService.getProductById((long) id).get());

        } else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            CustomerUser users = customerUserRepository.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("products", productService.getProductById((long) id).get());
        }
        return "viewitemafterlogin";
    }

    @GetMapping("/menuguest/viewmenu/{id}")
    public String ViewMenuItemguest(Model model ,@PathVariable int id) {
        CustomerUser user = new CustomerUser();
        user.setName("Guest");

        model.addAttribute("userDetails", user.getName());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getProductById((long) id).get());

        return "viewItem";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
//        List<Category> categories = categoryService.getCategoriesAndSize();
//        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.filterHighProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        return "menu";
    }


    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model) {
//        List<Category> categories = categoryService.getCategoriesAndSize();
//        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.filterLowerProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("products", products);
        return "menu";
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
       // List<Category> categoryDtos = categoryService.getCategoriesAndSize();
        List<ProductDto> productDtos = productService.searchProducts(keyword);
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews", listView);
       // model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", "Search Products");
        model.addAttribute("page", "Result Search");
        model.addAttribute("products", productDtos);
        return "menu";
    }








    @GetMapping("/reportForm")
    public String getRegPage(Model model) {
        model.addAttribute("problemForm", new ProblemForm());

        return "reportForm";
    }

    @PostMapping("/reportForm")
    public String saveUser(@ModelAttribute("problemForm") ProblemForm user, Model model) throws Exception {
        try {
            if (problemFormRepository.existsByEmail(user.getEmail())) {
                return String.valueOf(HttpStatus.BAD_REQUEST);
            } else
                user.setPhoneNumber("+1-" + user.getPhoneNumber());
            problemFormRepository.save(user);
            Date date = new Date();

            Properties server = propertiesServer();

            javax.mail.Session session = javax.mail.Session.getInstance(server, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });
            javax.mail.Message message = new MimeMessage(session);
            message.setSentDate(date);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Thank you for Contacting Kebab Grill");
            String newline = System.lineSeparator();
            UUID uuid = UUID.randomUUID();


//            MimeMultipart multipart = new MimeMultipart("text");
//            javax.mail.BodyPart bodyPart = new MimeBodyPart();
//            bodyPart.setText("Hi    "     + user.getName() + newline + "We will get back to you as soon as possible" + newline +  newline + "please keep the reference id "
//                    + uuid + " for future use " +newline + newline +
//                    "Thank you " +newline + "kebab Grill");
//            String html = "<h1>Kebab Grill</h1>img src = \"large.png\">";
//            bodyPart.setContent(html, "text/html");
//
//            multipart.addBodyPart(bodyPart);
//        DataSource dataSource = new FileDataSource("Kebab-grill website/src/main/resources/static/Images/large.png");
//            bodyPart.setDataHandler(new DataHandler(dataSource));
//            bodyPart.setHeader("Content-ID", "<image>");
//
//            multipart.addBodyPart(bodyPart);

            message.setText("Hi    "     + user.getName() + newline +newline +  "We will get back to you as soon as possible" + newline +  newline + "please keep the reference id "
                    + uuid + " for future use " +newline + newline +
                    "Thank you " +newline + "kebab Grill");

         //    message.setContent(multipart);

            Transport.send(message);
            System.out.println(" message " + new RuntimeException());
            System.out.println("email sent");

            model.addAttribute("message", "Submitted Successfully");
            return "reportForm";

        } catch (MessagingException e) {

            System.out.println("message " + e.getStackTrace());
            throw new RuntimeException(e.getCause());

        }
    }


    @GetMapping("/generate")
    public void generateQRCode(HttpServletResponse response) throws IOException {
        //String url = "http://localhost:8080/menu";// Replace with your menu URL
        String url = "https://youtube.com";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200);

            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            OutputStream outputStream = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            outputStream.close();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    @GetMapping("/logout")
    public String logout() {
        return "index";

    }






        public Properties propertiesServer() {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        return properties;
    }













    }


