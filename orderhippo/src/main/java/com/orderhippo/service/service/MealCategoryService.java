package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.MealCategoryBean;

public interface MealCategoryService {
	// 新增單筆類別資料
	public Object addMealCategory(MealCategoryBean mealCategoryBean);
	
	// 取得所有類別資料
	public List<MealCategoryBean> getAllMealCategory();
	
	// 修改類別資料
	public Object updateMealCategory(String requestID, MealCategoryBean mealCategoryBean);
}
