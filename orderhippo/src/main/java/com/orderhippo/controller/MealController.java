package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.MealBean;
import com.orderhippo.service.MealService;

import io.swagger.annotations.Api;

@Api("餐點資料API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class MealController {
	
	@Autowired
	private MealService mealService;
	
	@GetMapping("/meals")
	public List<MealBean> getAllMeals(){
		return mealService.getAllMeal();
	}

}
