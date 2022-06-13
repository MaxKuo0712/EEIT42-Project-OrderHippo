package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.MealBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.MealService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "餐點資料API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class MealController {
	
	@Autowired
	private MealService mealService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢餐點資料(mealstatus = true) by 餐點ID or 餐點種類ID or 店家ID or All")
	@GetMapping("/{requestID}/meals")
	public Object getAllMeals(
			@PathVariable String requestID,
			@RequestParam(name = "mealid", required = false) String mealId, 
			@RequestParam(name = "mealcategoryid", required = false) String mealcategoryId,
			@RequestParam(name = "storeid", required = false) String storeId,
			@RequestParam(name = "mealhot", required = false) Boolean mealHot,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (mealId != null && mealId.trim().length() > 0) {
				return mealService.getMealByMealID(mealId);
			} else if (mealcategoryId != null && mealcategoryId.trim().length() > 0) {
				return mealService.getMealByMealCategoryID(mealcategoryId);
			} else if (storeId != null && storeId.trim().length() > 0) {
				return mealService.getMealByStoreID(storeId);
			} else if (mealHot != null) {
				return mealService.getByMealhot(mealHot);
			} else {
				return mealService.getAllMeal();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("查詢餐點資料(mealstatus = true) by 餐點種類ID or 熱門 or All")
	@GetMapping("/meals")
	public Object getAllOpenMeals(
			@RequestParam(name = "mealcategoryid", required = false) String mealcategoryId,
			@RequestParam(name = "mealhot", required = false) Boolean mealHot) {

		if (mealcategoryId != null && mealcategoryId.trim().length() > 0) {
			return mealService.getMealByMealCategoryID(mealcategoryId);
		} else if (mealHot != null) {
			return mealService.getByMealhot(mealHot);
		} else {
			return mealService.getAllMeal();
		}
	}
	
	@ApiOperation("新增餐點資料")
	@PostMapping("/{requestID}/meal")
	public Object addMeal(
			@PathVariable String requestID,
			@RequestBody MealBean mealBean,
			@RequestParam(name = "token", required = true) String realHashToken) {

		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (mealBean != null) {
				mealBean.setCreatetid(requestID);
				return mealService.addMeal(mealBean);
			}
			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("修改餐點資料")
	@PutMapping("/{requestID}/meal")
	public Object updateMeal(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody MealBean mealBean) {

		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (mealBean != null) {
				return mealService.updateMeal(requestID, mealBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
		
//		if (mealBean == null) {
//			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//		} else {
//			String storeId = mealBean.getStoreid();
//			
//			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
//				return mealService.updateMeal(reviseId, mealBean);
//			} else {
//				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or StoreId", HttpStatus.BAD_REQUEST);
//			}
//		}

//		if ((mealBean != null) && ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) ) {
//			return mealService.updateMeal(reviseId, mealBean);
//		}
//		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
}
