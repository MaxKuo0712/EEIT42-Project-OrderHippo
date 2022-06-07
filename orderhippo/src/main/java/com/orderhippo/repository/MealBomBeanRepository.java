package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.MealBomBean;

public interface MealBomBeanRepository extends JpaRepository<MealBomBean, Integer> {
	
	// 刪除 By MealId
	List<MealBomBean> deleteByMealid(String mealid);
	
	// By MealBOMId 搜尋
	List<MealBomBean> findByBomid(String bomid);
	
	// By MealID 搜尋
	List<MealBomBean> findByMealid(String mealid);
	
	// By IngredientID 搜尋
	List<MealBomBean> findByIngredientid(String ingredientid);
	
	// By MealName + IngredientName 搜尋
//	List<MealBomBean> findByMealnameAndIngredientname(String mealname, String ingredientname);
	
//	// save
//	MealBomBean save(MealBomBean bom);
}
