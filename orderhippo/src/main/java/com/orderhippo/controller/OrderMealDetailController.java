package com.orderhippo.controller;

import java.util.ArrayList;
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

import com.orderhippo.model.OrderMealDetailBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.OrderMealDetailService;
import com.orderhippo.service.service.OrdersService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "訂單餐點資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class OrderMealDetailController {
	
	@Autowired
	private OrderMealDetailService orderMealDetailService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢訂單餐點資料 by 餐點ID or 訂單ID or All")
	@GetMapping("/{requestID}/ordermealdetails")
	public Object getAllOrderMealDetail(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name = "mealid", required = false) String mealId, 
			@RequestParam(name = "orderid", required = false) String orderId) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (mealId != null && mealId.trim().length() > 0) {
				return orderMealDetailService.getOrderMealDetailByMealid(mealId);
			} else if (orderId != null && orderId.trim().length() > 0) {
				return orderMealDetailService.getOrderMealDetailByOrderid(orderId);
			} else {
				return orderMealDetailService.getAllOrderMealDetail();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("新增訂單餐點資料")
	@PostMapping("/{requestID}/ordermealdetail")
	public Object addOrderMealDetail(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody List<OrderMealDetailBean> ListOrderMealDetailBean) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, null, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (ListOrderMealDetailBean.size() > 0) {
				for ( OrderMealDetailBean orderMealDetailBean : ListOrderMealDetailBean ) {
					orderMealDetailBean.setCreatetid(requestID);
				}
				return orderMealDetailService.addOrderMealDetail(ListOrderMealDetailBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
//	@ApiOperation("修改訂單餐點資料")
//	@PutMapping("/ordermealdetail/{reviseId}")
//	public Object updateMeal(@PathVariable String reviseId, @RequestBody OrderMealDetailBean orderMealDetailBean) {
//		
//		if (orderMealDetailBean == null) {
//			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//		} else {
//			String orderId = orderMealDetailBean.getOrderid();
//			String storeId = ordersService.getOrderByOrderId(orderId).get(0).getStoreid();
//			
//			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
//				return orderMealDetailService.(reviseId, orderMealDetailBean);
//			} else {
//				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or StoreId", HttpStatus.BAD_REQUEST);
//			}
//		}
//	}
}
