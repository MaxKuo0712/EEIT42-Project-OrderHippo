package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.MealCategoryBean;
import com.orderhippo.service.MealCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "商品類別資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class MealCategoryController {
	
	@Autowired
	private MealCategoryService mealCategoryService;
	
	@ApiOperation("查詢商品類別資料")
	@GetMapping("/mealcategorys")
	public List<MealCategoryBean> getAllMealCategory() {
		return mealCategoryService.getAllMealCategory();
	}
	
	@ApiOperation("新增商品類別資料")
	@PostMapping("/mealcategory")
	public boolean addMealCategory(@RequestBody MealCategoryBean mealCategoryBean) {
		if (mealCategoryBean != null) {
			return mealCategoryService.addMealCategory(mealCategoryBean);
		}
		return false;
	}
	
	@ApiOperation("修改商品類別資料")
	@PutMapping("/mealcategory/{reviseId}")
	public boolean updateMealCategory(@PathVariable String reviseId, @RequestBody MealCategoryBean mealCategoryBean) {
		String mealCategoryId = mealCategoryBean.getMealcategoryid();
		
		if ((mealCategoryBean != null) && ((reviseId.equals(mealCategoryId)) || (reviseId.equals("Admin")))) {
			return mealCategoryService.updateMealCategory(reviseId, mealCategoryBean);
		}
		
		return false;
	}
}
