package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealCategoryBean;
import com.orderhippo.repository.MealCategoryRepository;

@Service
public class MealCategoryServicelmp implements MealCategoryService {
	
	@Autowired
	private MealCategoryRepository mealCategoryRepository;

	@Override
	public MealCategoryBean addMealCategory(MealCategoryBean mealCategoryBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MealCategoryBean> getAllMealCategory() {
		List<MealCategoryBean> result = mealCategoryRepository.findAll();
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}

	@Override
	public Object updateMealCategory(String reviseId, String mealCategoryId, MealCategoryBean mealCategoryBean) {
		// TODO Auto-generated method stub
		return null;
	}

}
