package com.orderhippo.service.service;

import java.util.List;
import java.util.Optional;

import com.orderhippo.model.OrdersBean;

public interface OrdersService {
	// 新增單筆訂單
	public Object addOrder(OrdersBean ordersBean);
	
	// 查詢所有訂單
	public List<OrdersBean> getAllOrders();
	
	// 查詢單筆訂單 By OrdersID
	public List<OrdersBean> getOrderByOrderId(String orderid);
	
	// By OrdersID + ORDER_STATUS 搜尋
	public List<OrdersBean> getOrderByOrderidAndOrderstatus(String orderid, String orderstatus);
	
	// By StoreID 搜尋
	public List<OrdersBean> getOrderByStoreid(String storeid);
	
	// By StoreID + ORDER_STATUS 搜尋
	public List<OrdersBean> getOrderByStoreIDAndOrderStatus(String storeid, String orderstatus);
	
	// By USER_ID 搜尋
	public List<OrdersBean> getOrderByUserid(String userid);
	
	// By USER_ID + ORDER_STATUS 搜尋
	public List<OrdersBean> getOrderByUseridAndOrderstatus(String userid, String orderstatus);
	
	// By ORDER_STATUS 搜尋
	public List<OrdersBean> getOrderByOrderstatus(String orderstatus);
	
	// 修改訂單資料
	public Object updateOrder(String requestID, OrdersBean ordersBean);
}
