package com.orderhippo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.IngredientBean;


public interface IngredientRepository extends JpaRepository<IngredientBean, String> {

}
