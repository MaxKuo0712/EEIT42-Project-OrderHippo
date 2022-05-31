package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.MealBean;

public interface MealRepository extends JpaRepository<MealBean, Integer> {
	
	// By MealID 搜尋
	List<MealBean> findByMealidAndMealstatus(String mealid, Boolean mealstatus);
	
	// By MEAL_CATEGORY_ID 搜尋
	List<MealBean> findByMealcategoryidAndMealstatus(String mealcategoryid, Boolean mealstatus);
	
	// By STORE_ID 搜尋
	List<MealBean> findByStoreidAndMealstatus(String storeid, Boolean mealstatus);
	
	// 查詢熱門餐點 By MealHot
	List<MealBean> findByMealhot(Boolean mealhot);
}
