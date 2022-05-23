package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.OrdersBean;
import com.orderhippo.service.OrdersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("訂單資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class OrdersController {
	
	@Autowired 
	private OrdersService ordersService;
	
	@ApiOperation("查詢訂單資料 by 店家ID or 使用者ID or 訂單ID or All")
	@GetMapping("/orders")
	public List<OrdersBean> getOrederByOrderId(
			@RequestParam(name = "storeid", required = false) String storeId, 
			@RequestParam(name = "userid", required = false) String userId,
			@RequestParam(name = "orderid", required = false) String orderId) 
	{
		if (storeId != null && storeId.trim().length() != 0) {
			return ordersService.getOrderByStoreid(storeId);
		} else if (userId != null && userId.trim().length() != 0) {
			return ordersService.getOrderByUserid(userId);
		} else if (orderId != null && orderId.trim().length() != 0) {
			return ordersService.getOrderByOrderId(orderId);
		} else {
			return ordersService.getAllOrders();
		}
	}
	
	@ApiOperation("查詢訂單資料(+訂單狀態) by (店家ID or 使用者ID) and 訂單狀態")
	@GetMapping("/orders/findbyorderstatus")
	public List<OrdersBean> getOrderByStoreIDAndOrderStatus(
			@RequestParam(name = "orderid", required = false) String orderId,
			@RequestParam(name = "storeid", required = false) String storeId, 
			@RequestParam(name = "userid", required = false) String userId, 
			@RequestParam(name = "orderstatus", required = true) String orderStatus) 
	{
		if (storeId != null && storeId.trim().length() != 0) {
			return ordersService.getOrderByStoreIDAndOrderStatus(storeId, orderStatus);
		} else if (userId != null && userId.trim().length() != 0) {
			return ordersService.getOrderByUseridAndOrderstatus(userId, orderStatus);
		} else if (orderId != null && orderId.trim().length() != 0) {
			return ordersService.getOrderByOrderidAndOrderstatus(orderId, orderStatus);
		} else {
			return ordersService.getOrderByOrderstatus(orderStatus);
		}
	}
	
	@ApiOperation("新增單筆訂單資料")
	@PostMapping("/order")
	public Object addOrder(@RequestBody OrdersBean ordersBean) {
		if (ordersBean != null) {
			return ordersService.addOrder(ordersBean); 
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

}
