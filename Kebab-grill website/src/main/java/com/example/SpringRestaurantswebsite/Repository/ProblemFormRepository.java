package com.example.SpringRestaurantswebsite.Repository;


import com.example.SpringRestaurantswebsite.Model.ProblemForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProblemFormRepository extends JpaRepository<ProblemForm,Long>{

    ProblemForm findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);


}
