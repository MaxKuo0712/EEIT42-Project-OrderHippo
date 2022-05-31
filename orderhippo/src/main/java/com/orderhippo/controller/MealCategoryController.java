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

import com.orderhippo.model.MealCategoryBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.MealCategoryService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "商品類別資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class MealCategoryController {
	
	@Autowired
	private MealCategoryService mealCategoryService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢商品類別資料")
	@GetMapping("/{requestID}/mealcategorys")
	public Object getAllMealCategory(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			return mealCategoryService.getAllMealCategory();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
//	@ApiOperation("新增商品類別資料")
//	@PostMapping("/mealcategory")
//	public Object addMealCategory(@RequestBody MealCategoryBean mealCategoryBean) {
//		if (mealCategoryBean != null) {
//			return mealCategoryService.addMealCategory(mealCategoryBean);
//		}
//		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//	}
	
//	@ApiOperation("修改商品類別資料")
//	@PutMapping("/mealcategory/{reviseId}")
//	public Object updateMealCategory(@PathVariable String reviseId, @RequestBody MealCategoryBean mealCategoryBean) {
//		
//		if (mealCategoryBean == null) {
//			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//		} else if (!reviseId.equals("Admin")) {
//			return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin", HttpStatus.BAD_REQUEST);
//		} else {
//			return mealCategoryService.updateMealCategory(reviseId, mealCategoryBean);
//		}
//		
////		if ((mealCategoryBean != null) && (reviseId.equals("Admin"))) {
////			return mealCategoryService.updateMealCategory(reviseId, mealCategoryBean);
////		}
////		return false;
//	}
}
