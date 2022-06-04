package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.StoreCouponBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreCouponService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "店家優惠券API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class StoreCouponController {
	@Autowired
	private StoreCouponService storeCouponService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	// 新增單筆優惠卷
	@ApiOperation("新增單筆Coupon")
	@PostMapping(path="/{requestID}/coupon")
	public Object addStoreCoupon(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestBody StoreCouponBean storeCouponBean) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(storeCouponBean != null) {
				storeCouponBean.setCreateid(requestID);
				return storeCouponService.addStoreCoupon(storeCouponBean);
			} else {
				return new ResponseEntity<String>("Input not found", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 查詢優惠卷資料 by CouponID 或 StoreID 或 ALL
	@ApiOperation("查詢優惠卷 by CouponID 或 StoreID 或 ALL")
	@GetMapping(path="/{requestID}/coupons")
	public Object getCoupons(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name="storeid", required=false) String storeId,
			@RequestParam(name="couponname", required=false) String couponName) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(storeId != null && storeId.trim().length() != 0) {
				return storeCouponService.getStoreCouponByStoreId(storeId);
			} else if(couponName != null && couponName.trim().length() != 0) {
				return storeCouponService.getStoreCouponByCouponName(couponName);
			} else {
				return storeCouponService.getAllCoupons();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 修改單筆優惠卷 PutMapping
	@ApiOperation("更新優惠卷資料")
	@PutMapping(path="/{requestID}/coupon", consumes="application/json")
	public Object patchCoupon(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestBody StoreCouponBean storeCouponBean) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(storeCouponBean != null) {
				return storeCouponService.updateStoreCoupon(requestID, storeCouponBean);
			} else {
				return new ResponseEntity<String>("Input not found", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		} 
	}
	
//	// 刪除單筆優惠卷
//	@ApiOperation("刪除優惠卷")
//	@DeleteMapping(path="/{requestID}/coupon")
//	public Object removeCoupon(
//			@PathVariable String requestID,
//			@RequestParam(name="token", required=true) String realHashToken,
//			@RequestBody StoreCouponBean storeCouponBean) {
//		
//		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
//		
//		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
//		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
//		
//		if(verifyResult) {
//			var isRemoved = storeCouponService.deleteCoupon(requestID, storeCouponBean);
//			if(isRemoved != null) {
//				return new ResponseEntity<Boolean>(true, HttpStatus.OK); 
//			} else {
//				return new ResponseEntity<String>("Input not found", HttpStatus.NOT_FOUND);
//			}
//			
//		} else {
//			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
//		}
//	}
	
	// 刪除單筆優惠卷
	@ApiOperation("刪除優惠卷")
	@DeleteMapping(path="/{requestID}/coupon")
	public Object removeCoupon(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestParam(name="couponid", required=true) String couponId) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if (couponId != null && couponId.trim().length() != 0) {
				return storeCouponService.deleteCoupon(requestID, couponId);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}			
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
}
