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
	public boolean addMealCategory(MealCategoryBean mealCategoryBean) {
		if (mealCategoryBean != null) {
			try {
				mealCategoryRepository.save(mealCategoryBean);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
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
	public boolean updateMealCategory(String reviseId, MealCategoryBean mealCategoryBean) {
		List<MealCategoryBean> mealCateory = mealCategoryRepository.findByMealcategoryid(mealCategoryBean.getMealcategoryid());
		
		if (mealCateory.size() == 1) {
			try {
				mealCategoryRepository.save(mealCategoryBean);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}

}
