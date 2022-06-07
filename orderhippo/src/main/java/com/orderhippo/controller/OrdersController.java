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

import com.orderhippo.model.OrdersBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.OrdersService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "訂單資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class OrdersController {
	
	@Autowired 
	private OrdersService ordersService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢訂單資料 by 店家ID or 使用者ID or 訂單ID or All")
	@GetMapping("/{requestID}/orders")
	public Object getOrederByOrderId(
			@PathVariable String requestID,
			@RequestParam(name = "storeid", required = false) String storeId, 
			@RequestParam(name = "userid", required = false) String userId,
			@RequestParam(name = "orderid", required = false) String orderId,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (storeId != null && storeId.trim().length() != 0) {
				return ordersService.getOrderByStoreid(storeId);
			} else if (userId != null && userId.trim().length() != 0) {
				return ordersService.getOrderByUserid(userId);
			} else if (orderId != null && orderId.trim().length() != 0) {
				return ordersService.getOrderByOrderId(orderId);
			} else {
				return ordersService.getAllOrders();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("查詢訂單資料(+訂單狀態) by (店家ID or 使用者ID) and 訂單狀態")
	@GetMapping("/{requestID}/orders/findbyorderstatus")
	public Object getOrderByStoreIDAndOrderStatus(
			@PathVariable String requestID,
			@RequestParam(name = "orderid", required = false) String orderId,
			@RequestParam(name = "storeid", required = false) String storeId, 
			@RequestParam(name = "userid", required = false) String userId, 
			@RequestParam(name = "orderstatus", required = true) String orderStatus,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (storeId != null && storeId.trim().length() != 0) {
				return ordersService.getOrderByStoreIDAndOrderStatus(storeId, orderStatus);
			} else if (userId != null && userId.trim().length() != 0) {
				return ordersService.getOrderByUseridAndOrderstatus(userId, orderStatus);
			} else if (orderId != null && orderId.trim().length() != 0) {
				return ordersService.getOrderByOrderidAndOrderstatus(orderId, orderStatus);
			} else {
				return ordersService.getOrderByOrderstatus(orderStatus);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("新增單筆訂單資料")
	@PostMapping("/{requestID}/order")
	public Object addOrder(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody OrdersBean ordersBean) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, null, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (ordersBean != null) {
				ordersBean.setCreatetid(requestID);
				return ordersService.addOrder(ordersBean);
			}
			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("更新單筆訂單資料")
	@PutMapping("/{requestID}/order")
	public Object updateOrder(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody OrdersBean ordersBean) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (ordersBean != null) {
				return ordersService.updateOrder(requestID, ordersBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}

}
