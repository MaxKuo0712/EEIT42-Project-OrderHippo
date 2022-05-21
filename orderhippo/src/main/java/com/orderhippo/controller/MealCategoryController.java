package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping("/mealcategory")
	public List<MealCategoryBean> getAllMealCategory() {
		return mealCategoryService.getAllMealCategory();
	}
}
