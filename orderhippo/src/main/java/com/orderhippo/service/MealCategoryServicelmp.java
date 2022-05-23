package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealCategoryBean;
import com.orderhippo.repository.MealCategoryRepository;

@Service
public class MealCategoryServicelmp implements MealCategoryService {
	
	@Autowired
	private MealCategoryRepository mealCategoryRepository;

	@Override
	public Object addMealCategory(MealCategoryBean mealCategoryBean) {
		if (mealCategoryBean != null) {
			try {
				mealCategoryRepository.save(mealCategoryBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

	@Override
	public List<MealCategoryBean> getAllMealCategory() {
		List<MealCategoryBean> result = mealCategoryRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	@Override
	public Object updateMealCategory(String reviseId, MealCategoryBean mealCategoryBean) {
		List<MealCategoryBean> mealCateory = mealCategoryRepository.findByMealcategoryid(mealCategoryBean.getMealcategoryid());
		
		if (mealCateory.size() == 1) {
			try {
				mealCategoryRepository.save(mealCategoryBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：MealCategoryID", HttpStatus.NOT_FOUND);
	}

}
