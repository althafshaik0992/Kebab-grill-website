//package com.example.SpringRestaurantswebsite.Config;
//
//
//import com.example.SpringRestaurantswebsite.Service.CustomerDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import com.example.SpringRestaurantswebsite.Config.googleOAuth2SuccessHandler;
//import org.springframework.util.AntPathMatcher;
//
//
//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfig  {
//
//   @Autowired
//   googleOAuth2SuccessHandler googleOAuthSuccessHandler;
//
//   @Autowired
//   CustomerDetailsService customerDetailsService;
//
//
//
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests().antMatchers("/","/menu/**","/register"
////        ,"/**").permitAll().antMatchers("/","/admin/**","/").hasAnyRole("ADMIN")
////                .anyRequest().authenticated().and().formLogin()
////                .loginPage("/login").permitAll()
////                .failureUrl("/failure?error=true")
////                .defaultSuccessUrl("/")
////                .usernameParameter("email")
////                .passwordParameter("password")
////                .and()
////                .oauth2Login()
////                .loginPage("/login")
////                .successHandler(googleOAuthSuccessHandler)
////                .and()
////                .logout()
////                .logoutRequestMatcher((RequestMatcher) new AntPathMatcher("/logout"))
////                .logoutSuccessUrl("/login")
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID")
////                .and()
////                .exceptionHandling()
////                .and()
////                .csrf()
////                .disable();
////
////        http.headers().frameOptions().disable();
////
////    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.csrf().disable()
//                .authorizeRequests().requestMatchers("/","/menu/**","/register"
//                        ,"/**").permitAll().requestMatchers("/","/admin/**","/").hasAnyRole("ADMIN")
//                .anyRequest().authenticated().and().formLogin()
//                .loginPage("/login").permitAll()
//                .failureUrl("/failure?error=true")
//                .defaultSuccessUrl("/")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .successHandler(googleOAuthSuccessHandler)
//                .and()
//                .logout()
//                .logoutRequestMatcher((RequestMatcher) new AntPathMatcher("/logout"))
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .exceptionHandling()
//                .and()
//                .csrf()
//                .disable();
//
////         http.headers().frameOptions().disable();
//
//        return (SecurityFilterChain) http.headers().frameOptions().disable();
//    }
//
//
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////          auth.userDetailsService(customerDetailsService);
////    }
//
//
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////       web.ignoring().antMatchers("/resources/**","/static/**","/css/**",
////       "/Images/**","/productImages/**");
////    }
//}
