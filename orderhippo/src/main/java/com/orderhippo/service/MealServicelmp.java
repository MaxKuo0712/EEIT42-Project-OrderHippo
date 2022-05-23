package com.orderhippo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealBean;
import com.orderhippo.repository.MealRepository;

@Service
public class MealServicelmp implements MealService {
	
	@Autowired
	private MealRepository mealRepository;
	
	// 新增單筆餐點
	@Override
	public boolean addMeal(MealBean mealBean) {
		
		if (mealBean != null) {
			
			try {
				MealBean result = mealRepository.save(mealBean);
				return mealRepository.existsById(result.getId());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	// 查詢所有餐點
	@Override
	public List<MealBean> getAllMeal() {
		List<MealBean> result = mealRepository.findAll();
		
		if (!result.isEmpty()) {
			return mealRepository.findAll();
		}
		return null;
	}

	// 修改單筆餐點
	@Override
	public boolean updateMeal(String reviseId, MealBean mealBean) {
		List<MealBean> meal = mealRepository.findByMealid(mealBean.getMealid());
		
		if (meal.size() == 1) {
			MealBean currentMeal = meal.get(0);
			
			mealBean.setId(currentMeal.getId());
			mealBean.setStoreid(currentMeal.getStoreid());
			mealBean.setReviseid(reviseId);
			mealBean.setRevisetime(new Date());
			
			try {
				mealRepository.save(mealBean);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	// 刪除單筆餐點
	@Override
	public boolean deleteMeal(String mealId) {
		// TODO Auto-generated method stub
		return false;
	}

	// 查詢餐點 By MealID 搜尋
	@Override
	public List<MealBean> getMealByMealID(String mealId) {
		List<MealBean> result = mealRepository.findByMealid(mealId);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 查詢餐點 By MEAL_CATEGORY_ID 搜尋
	@Override
	public List<MealBean> getMealByMealCategoryID(String mealcategoryId) {
		List<MealBean> result = mealRepository.findByMealcategoryid(mealcategoryId);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 查詢餐點 By STORE_ID 搜尋
	@Override
	public List<MealBean> getMealByStoreID(String storeId) {
		List<MealBean> result = mealRepository.findByStoreid(storeId);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
}
