package com.orderhippo.service;

import java.util.List;

import com.orderhippo.model.MealBean;

public interface MealService {
	
	// 新增單筆餐點
	public MealBean addMeal(MealBean mealBean);
	
	// 查詢所有餐點
	public List<MealBean> getAllMeal();

	// 查詢餐點 By MealID 搜尋
	
	// 查詢餐點 By MEAL_CATEGORY_ID 搜尋
	
	// 查詢餐點 By STORE_ID 搜尋
	
	// 修改單筆餐點
	public Object updateMeal(String reviseId, String mealId, MealBean mealBean);
	
	// 刪除單筆餐點
	public Object deleteMeal(String mealId);
}
