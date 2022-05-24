package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.IngredientBean;
import com.orderhippo.service.service.IngredientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "食材資料API")
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class IngredientController {
	
	@Autowired IngredientService ingredientService;
	
	@ApiOperation("取得所有食材資訊")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/ingredients")
    public List<IngredientBean> getAllIngredients() {
        return ingredientService.getAllIngredient();
    }

}
