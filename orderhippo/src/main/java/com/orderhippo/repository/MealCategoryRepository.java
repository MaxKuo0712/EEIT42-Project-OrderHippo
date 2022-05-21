package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.MealCategoryBean;

public interface MealCategoryRepository extends JpaRepository<MealCategoryBean, Integer> {
	
	// By Mealcategoryid 搜尋 
	List<MealCategoryBean> findByMealcategoryid(String mealcategoryid);
}
