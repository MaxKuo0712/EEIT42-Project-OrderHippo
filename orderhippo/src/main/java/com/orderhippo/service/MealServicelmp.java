package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealBean;
import com.orderhippo.repository.MealRepository;

@Service
public class MealServicelmp implements MealService {
	
	@Autowired
	private MealRepository mealRepository;

	@Override
	public MealBean addMeal(MealBean mealBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MealBean> getAllMeal() {
		List<MealBean> result = mealRepository.findAll();
		
		if (result.size() > 0) {
			return mealRepository.findAll();
		}
		return null;
	}

	@Override
	public Object updateMeal(String reviseId, String mealId, MealBean mealBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteMeal(String mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MealBean getMealByMealID(String mealId) {
		List<MealBean> result = mealRepository.findByMealid(mealId);
		
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<MealBean> getMealByMealCategoryID(String mealcategoryId) {
		List<MealBean> result = mealRepository.findByMealcategoryid(mealcategoryId);
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}

	@Override
	public List<MealBean> getMealByStoreID(String storeId) {
		List<MealBean> result = mealRepository.findByStoreid(storeId);
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}
}
