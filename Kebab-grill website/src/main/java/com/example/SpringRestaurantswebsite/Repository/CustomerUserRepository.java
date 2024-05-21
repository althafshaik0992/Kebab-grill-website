package com.example.SpringRestaurantswebsite.Repository;

import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Integer> {

     CustomerUser findByEmail(String emailId);

      boolean existsByEmail(String emailId);


}
