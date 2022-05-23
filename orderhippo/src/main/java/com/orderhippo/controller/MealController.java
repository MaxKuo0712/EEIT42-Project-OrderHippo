package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.MealBean;
import com.orderhippo.service.MealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("餐點資料API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class MealController {
	
	@Autowired
	private MealService mealService;
	
	@ApiOperation("查詢餐點資料 by 餐點ID or 餐點種類ID or 店家ID or All")
	@GetMapping("/meals")
	public List<MealBean> getAllMeals(
			@RequestParam(name = "mealid", required = false) String mealId, 
			@RequestParam(name = "mealcategoryid", required = false) String mealcategoryId,
			@RequestParam(name = "storeid", required = false) String storeId)
	{
		if (mealId != null && mealId.trim().length() > 0) {
			return mealService.getMealByMealID(mealId);
		} else if (mealcategoryId != null && mealcategoryId.trim().length() > 0) {
			return mealService.getMealByMealCategoryID(mealcategoryId);
		} else if (storeId != null && storeId.trim().length() > 0) {
			return mealService.getMealByStoreID(storeId);
		} else {
			return mealService.getAllMeal();
		}
	}
	
	@ApiOperation("新增餐點資料")
	@PostMapping("/meal")
	public Object addMeal(@RequestBody MealBean mealBean) {
		if (mealBean != null) {
			return mealService.addMeal(mealBean);
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation("修改餐點資料")
	@PutMapping("/meal/{reviseId}")
	public Object updateMeal(@PathVariable String reviseId, @RequestBody MealBean mealBean) {
		
		if (mealBean == null) {
			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
		} else {
			String storeId = mealBean.getStoreid();
			
			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
				return mealService.updateMeal(reviseId, mealBean);
			} else {
				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or StoreId", HttpStatus.BAD_REQUEST);
			}
		}
		
//		if ((mealBean != null) && ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) ) {
//			return mealService.updateMeal(reviseId, mealBean);
//		}
//		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

}
