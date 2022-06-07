package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.OrderMealDetailBean;

public interface OrderMealDetailService {
	// 新增訂單餐點資訊
	public Object addOrderMealDetail(List<OrderMealDetailBean> ListOrderMealDetailBean);
	
	// 查詢訂單餐點資訊
	public List<OrderMealDetailBean> getAllOrderMealDetail();
	
	// 查詢訂單餐點資訊 By ORDER_ID
	public List<OrderMealDetailBean> getOrderMealDetailByOrderid(String orderid);
	
	// 查詢訂單餐點資訊 By MEAL_ID
	public List<OrderMealDetailBean> getOrderMealDetailByMealid(String mealid);
	
	// 修改訂單餐點資訊
//	public Object updateOrderMealDetail(String reviseId, OrderMealDetailBean orderMealDetailBean);
}
