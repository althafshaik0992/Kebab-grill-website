//package com.example.SpringRestaurantswebsite.Config;
//
//
//import com.example.SpringRestaurantswebsite.Model.LoginUser;
//import com.example.SpringRestaurantswebsite.Model.Roles;
//import com.example.SpringRestaurantswebsite.Repository.LoginFormRepository;
//import com.example.SpringRestaurantswebsite.Repository.RoleRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.stereotype.Component;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.config.annotation.web.configurers.AbstractConfigAttributeRequestMatcherRegistry;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class googleOAuth2SuccessHandler implements AuthenticationSuccessHandler{
//
//    @Autowired
//    LoginFormRepository  formRepository;
//
//
//    @Autowired
//    RoleRepository roleRepository;
//
//
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//        String email = token.getPrincipal().getAttributes().get("email").toString();
//        if (formRepository.findUserByEmail(email).isPresent()) {
//
//        } else {
//            LoginUser loginUser = new LoginUser();
//            loginUser.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
//            loginUser.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
//            loginUser.setEmail(email);
//            List<Roles> rolesList = new ArrayList<>();
//            rolesList.add(roleRepository.findById(2).get());
//            loginUser.setRoles(rolesList);
//            formRepository.save(loginUser);
//
//        }
//        redirectStrategy.sendRedirect(request, response, "/");
//    }
//
//
//}
//
//
//
