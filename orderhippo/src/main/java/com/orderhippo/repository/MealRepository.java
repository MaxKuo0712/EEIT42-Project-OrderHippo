package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.MealBean;

public interface MealRepository extends JpaRepository<MealBean, Integer> {
	
	// By MealID 搜尋
	List<MealBean> findByMealid(String mealid);
	
	// By MEAL_CATEGORY_ID 搜尋
	List<MealBean> findByMealcategoryid(String mealcategoryid);
	
	// By STORE_ID 搜尋
	List<MealBean> findByStoreid(String storeid);
	
}
