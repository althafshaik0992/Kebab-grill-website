package com.example.SpringRestaurantswebsite.Controller;




import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import com.example.SpringRestaurantswebsite.Repository.ProblemFormRepository;
import com.example.SpringRestaurantswebsite.Service.CategoryService;
import com.example.SpringRestaurantswebsite.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;


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
            return "test";

        } catch (MessagingException e) {

            System.out.println("message " + e.getStackTrace());
            throw new RuntimeException(e.getCause());

        }
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


