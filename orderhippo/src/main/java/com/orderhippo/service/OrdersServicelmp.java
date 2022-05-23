package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.OrdersBean;
import com.orderhippo.repository.OrdersRepository;

@Service
public class OrdersServicelmp implements OrdersService {
	
	@Autowired
	private OrdersRepository ordersRepository;

	@Override
	public Object addOrder(OrdersBean ordersBean) {
		
		if (ordersBean != null) {
			try {
				OrdersBean result = ordersRepository.save(ordersBean);
				return new ResponseEntity<Boolean>(ordersRepository.existsById(result.getId()), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

	@Override
	public List<OrdersBean> getAllOrders() {
		List<OrdersBean> result = ordersRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} 
		return null;
	}

	// By OrdersID 搜尋
	@Override
	public List<OrdersBean> getOrderByOrderId(String orderId) {
		List<OrdersBean> result = ordersRepository.findByOrderid(orderId);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// By OrdersID + ORDER_STATUS 搜尋
	@Override
	public List<OrdersBean> getOrderByOrderidAndOrderstatus(String orderid, String orderstatus) {
		List<OrdersBean> result = ordersRepository.findByOrderidAndOrderstatus(orderid, orderstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// By StoreID 搜尋
	@Override
	public List<OrdersBean> getOrderByStoreid(String storeid) {
		List<OrdersBean> result = ordersRepository.findByStoreid(storeid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// By StoreID + ORDER_STATUS 搜尋
	@Override
	public List<OrdersBean> getOrderByStoreIDAndOrderStatus(String storeid, String orderstatus) {
		List<OrdersBean> result = ordersRepository.findByStoreidAndOrderstatus(storeid, orderstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// By USER_ID 搜尋
	@Override
	public List<OrdersBean> getOrderByUserid(String userid) {
		List<OrdersBean> result = ordersRepository.findByUserid(userid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// By USER_ID + ORDER_STATUS 搜尋
	@Override
	public List<OrdersBean> getOrderByUseridAndOrderstatus(String userid, String orderstatus) {
		List<OrdersBean> result = ordersRepository.findByUseridAndOrderstatus(userid, orderstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// By ORDER_STATUS 搜尋
	@Override
	public List<OrdersBean> getOrderByOrderstatus(String orderstatus) {
		List<OrdersBean> result = ordersRepository.findByOrderstatus(orderstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	@Override
	public Object updateOrder(String reviseId, String orderId, OrdersBean ordersBean) {
		// TODO Auto-generated method stub
		return null;
	}
}
