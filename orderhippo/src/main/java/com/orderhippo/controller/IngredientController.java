package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.IngredientBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.service.service.IngredientService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "食材資料API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class IngredientController {
	
	@Autowired 
	private IngredientService ingredientService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("取得所有食材資訊")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/{requestID}/ingredients")
    public Object getAllIngredients(
    		@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			 return ingredientService.getAllIngredient();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
    }
}
