package com.orderhippo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderhippo.model.OrderMealDetailBean;
import com.orderhippo.repository.OrderMealDetailRepository;

@Service
public class OrderMealDetailServicelmp implements OrderMealDetailService {
	
	@Autowired
	private OrderMealDetailRepository orderMealDetailRepository;

	@Override
	public boolean addOrderMealDetail(OrderMealDetailBean orderMealDetailBean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OrderMealDetailBean> getAllOrderMealDetail() {
		List<OrderMealDetailBean> result = orderMealDetailRepository.findAll();
		
		if (result.size() > 0) {
			return result;
		}
		
		return null;
	}

	@Override
	public List<OrderMealDetailBean> getOrderMealDetailByOrderid(String orderid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderMealDetailBean> getOrderMealDetailByMealid(String mealid) {
		// TODO Auto-generated method stub
		return null;
	}

}
