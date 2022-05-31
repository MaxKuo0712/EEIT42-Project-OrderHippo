package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.MealBean;
import com.orderhippo.model.MealBomBean;

public interface MealService {
	
	// 新增單筆餐點
	public Object addMeal(MealBean mealBean);
	
	// 查詢所有餐點
	public List<MealBean> getAllMeal();

	// 查詢餐點 By MealID 搜尋
	public List<MealBean> getMealByMealID(String mealId);
	
	// 查詢餐點 By MEAL_CATEGORY_ID 搜尋
	public List<MealBean> getMealByMealCategoryID(String mealcategoryId);
	
	// 查詢餐點 By STORE_ID 搜尋
	public List<MealBean> getMealByStoreID(String storeId);
	
	// 修改單筆餐點
	public Object updateMeal(String requestID, MealBean mealBean);
	
	// 查詢熱門餐點 By MealHot
	public List<MealBean> getByMealhot(Boolean mealhot);
	
	// 刪除單筆餐點
//	public Object deleteMeal(String mealId);
}
