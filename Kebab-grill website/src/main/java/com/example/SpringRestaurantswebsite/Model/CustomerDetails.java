//package com.example.SpringRestaurantswebsite.Model;
//
//import com.example.SpringRestaurantswebsite.Model.LoginUser;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//
//public class CustomerDetails  extends LoginUser implements UserDetails {
//
//
//    public CustomerDetails(LoginUser loginUser){
//        super(loginUser);
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        super.getRoles().forEach(roles -> {
//            authorities.add((new SimpleGrantedAuthority(roles.getName())));
//        });
//
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return super.getEmail();
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
