package com.example.SpringRestaurantswebsite.Repository;

import com.example.SpringRestaurantswebsite.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String name);


}
