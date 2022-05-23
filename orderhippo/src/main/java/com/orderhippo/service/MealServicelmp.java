package com.orderhippo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealBean;
import com.orderhippo.repository.MealRepository;

@Service
public class MealServicelmp implements MealService {
	
	@Autowired
	private MealRepository mealRepository;
	
	// 新增單筆餐點
	@Override
	public Object addMeal(MealBean mealBean) {
		
		if (mealBean != null) {
			try {
				MealBean result = mealRepository.save(mealBean);
				return new ResponseEntity<Boolean>(mealRepository.existsById(result.getId()), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

	// 查詢所有餐點
	@Override
	public List<MealBean> getAllMeal() {
		List<MealBean> result = mealRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 修改單筆餐點
	@Override
	public Object updateMeal(String reviseId, MealBean mealBean) {
		List<MealBean> meal = mealRepository.findByMealid(mealBean.getMealid());
		
		if (meal.size() == 1) {
			MealBean currentMeal = meal.get(0);
			
			mealBean.setId(currentMeal.getId());
			mealBean.setStoreid(currentMeal.getStoreid());
			mealBean.setReviseid(reviseId);
			mealBean.setRevisetime(new Date());
			
			try {
				mealRepository.save(mealBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：MealID", HttpStatus.NOT_FOUND);
	}

	// 刪除單筆餐點
	@Override
	public Object deleteMeal(String mealId) {
		List<MealBean> result = mealRepository.findByMealid(mealId);
		
		if (result.size() == 1) {
			try {
				mealRepository.deleteById(result.get(0).getId());
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：MealID", HttpStatus.NOT_FOUND);
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
