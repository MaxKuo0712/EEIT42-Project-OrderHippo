package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.OrdersBean;

public interface OrdersRepository extends JpaRepository<OrdersBean, Integer> {
	
	// By OrdersID 搜尋
	List<OrdersBean> findByOrderid(String orderid);
	
	// By OrdersID + ORDER_STATUS 搜尋
	List<OrdersBean> findByOrderidAndOrderstatus(String orderid, Integer orderstatus);
	
	// By StoreID 搜尋
	List<OrdersBean> findByStoreid(String storeid);
	
	// By StoreID + ORDER_STATUS 搜尋
	List<OrdersBean> findByStoreidAndOrderstatus(String storeid, Integer orderstatus);
	
	// By USER_ID 搜尋
	List<OrdersBean> findByUserid(String userid);
	
	// By USER_ID + ORDER_STATUS 搜尋
	List<OrdersBean> findByUseridAndOrderstatus(String userid, Integer orderstatus);
	
	// By ORDER_STATUS 搜尋
	List<OrdersBean> findByOrderstatus(Integer orderstatus);
	
}
