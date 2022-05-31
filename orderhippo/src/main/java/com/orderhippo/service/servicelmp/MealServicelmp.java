package com.orderhippo.service.servicelmp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.MealBean;
import com.orderhippo.repository.MealRepository;
import com.orderhippo.service.service.MealService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class MealServicelmp implements MealService {
	
	@Autowired
	private MealRepository mealRepository;
	
	// 新增單筆餐點
	@Override
	public Object addMeal(MealBean mealBean) {
		
		if (mealBean != null) {
			try {
				MealBean result = mealRepository.save(mealBean);
				return new ResponseEntity<MealBean>(result, HttpStatus.OK);
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
	public Object updateMeal(String requestID, MealBean mealBean) {
		List<MealBean> meal = mealRepository.findByMealidAndMealstatus(mealBean.getMealid(), true);
		
		if (meal.size() == 1) {
			MealBean currentMeal = meal.get(0);
			
			mealBean.setId(currentMeal.getId());
			mealBean.setStoreid(currentMeal.getStoreid());
			mealBean.setReviseid(requestID);
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

	// 刪除單筆餐點 -- 更改餐點狀態為false
//	@Override
//	public Object deleteMeal(String mealId) {
//		List<MealBean> result = mealRepository.findByMealidAndMealstatus(mealId, true);
//		
//		if (result.size() == 1) {
//			try {
//				MealBean targetMeal = result.get(0);
//				targetMeal.setMealstatus(false);
//				mealRepository.save(targetMeal);
//				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
//		return new ResponseEntity<String>("資料不存在：MealID", HttpStatus.NOT_FOUND);
//		
////		if (result.size() == 1) {
////			try {
////				mealRepository.deleteById(result.get(0).getId());
////				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
////			} catch (Exception e) {
////				e.printStackTrace();
////				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
////			}
////		}
//		
//	}

	// 查詢餐點 By MealID 搜尋
	@Override
	public List<MealBean> getMealByMealID(String mealId) {
		List<MealBean> result = mealRepository.findByMealidAndMealstatus(mealId, true);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 查詢餐點 By MEAL_CATEGORY_ID 搜尋
	@Override
	public List<MealBean> getMealByMealCategoryID(String mealcategoryId) {
		List<MealBean> result = mealRepository.findByMealcategoryidAndMealstatus(mealcategoryId, true);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 查詢餐點 By STORE_ID 搜尋
	@Override
	public List<MealBean> getMealByStoreID(String storeId) {
		List<MealBean> result = mealRepository.findByStoreidAndMealstatus(storeId, true);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	@Override
	public List<MealBean> getByMealhot(Boolean mealhot) {
		List<MealBean> result = mealRepository.findByMealhot(mealhot);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
}
