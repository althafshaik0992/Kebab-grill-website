package com.example.SpringRestaurantswebsite.Repository;

import com.example.SpringRestaurantswebsite.Model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoginFormRepository extends JpaRepository<LoginUser, Integer> {
    LoginUser findByEmailIgnoreCase(String email);

        Optional<LoginUser> findUserByEmail(String email);
}
