package com.orderhippo.service;

import java.util.List;

import com.orderhippo.model.MealCategoryBean;

public interface MealCategoryService {
	// 新增單筆類別資料
	public boolean addMealCategory(MealCategoryBean mealCategoryBean);
	
	// 取得所有類別資料
	public List<MealCategoryBean> getAllMealCategory();
	
	// 修改類別資料
	public boolean updateMealCategory(String reviseId, MealCategoryBean mealCategoryBean);
}
