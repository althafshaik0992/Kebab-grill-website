package com.example.SpringRestaurantswebsite.Service;

import com.example.SpringRestaurantswebsite.Dto.UserRegisteredDTO;
import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.PasswordResetToken;
import com.example.SpringRestaurantswebsite.Model.Role;
import com.example.SpringRestaurantswebsite.Repository.CustomerUserRepository;
import com.example.SpringRestaurantswebsite.Repository.RoleRepository;
import com.example.SpringRestaurantswebsite.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {


   @Autowired
    CustomerUserRepository customerUserRepository;

    @Autowired
    RoleRepository roleRepo;


    @Autowired
    TokenRepository tokenRepository;

    @Value("${spring.mail.username}")
    private String username;

    private final String fromEmail = "shaikguf15080@gmail.com";
//
//    private final String toEmaile = "sk.murfu@gmail.com";


    @Value("${spring.mail.password}")
    private String password;







    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CustomerUser user = customerUserRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }












    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public CustomerUser save(UserRegisteredDTO userRegisteredDTO) {
        Role role = roleRepo.findByRole("USER");

        CustomerUser user = new CustomerUser();
        user.setEmail(userRegisteredDTO.getEmail_id());
        user.setName(userRegisteredDTO.getName());
        user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
        user.setRole(role);

        return customerUserRepository.save(user);
    }

    @Override
    public boolean exists(String emailId) {
        return customerUserRepository.existsByEmail(emailId);
    }

    public String sendEmail(CustomerUser user) {
        try {

            Properties server = propertiesServer();
            String resetLink = generateResetToken(user);
            javax.mail.Session session = javax.mail.Session.getInstance(server, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("TPlease Reset Your Password - kebab Grill");
            message.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
                    + "Regards \n" + "ABC");

            Transport.send(message);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }


    public String generateResetToken(CustomerUser user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUser(user);
        PasswordResetToken token = tokenRepository.save(resetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/resetPassword";
            return endpointUrl + "/" + resetToken.getToken();
        }
        return "";
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


    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }
}
