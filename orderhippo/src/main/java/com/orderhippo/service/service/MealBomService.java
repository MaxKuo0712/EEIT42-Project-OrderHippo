package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.MealBomBean;

public interface MealBomService {
	// 新增單筆BOM
	public Object addBOM(List<MealBomBean> listMealBomBean);
	
	// 查詢所有 Meal BOM
	public List<MealBomBean> getAllMealbom();
	
	// 查詢單筆BOM By BOMId
	public List<MealBomBean> getBomByBomid(String bomid);
	
	// 查詢單筆BOM By MealId
	public List<MealBomBean> getBomByMealid(String mealid);
	
	// 查詢單筆BOM By IngredientId
	public List<MealBomBean> getBomByIngredientid(String ingredientid);
	
	// 刪除單筆BOM資料
	public Object deleteBom(String requestID, String mealid);
	
//	// 查詢單筆BOM By MealName
//	public List<MealBomBean> getBomByMealname(String mealname);
	
//	// 查詢單筆BOM By MealName + IngredientName
//	public List<MealBomBean> getBomByMealnameAndIngredientname(String mealname, String ingredientname);
	
	// 修改BOM資料
	public Object updateMealbom(String requestID, MealBomBean mealBomBean);

}
