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
		
		if (orderMealDetailBean != null) {
			try {
				orderMealDetailRepository.save(orderMealDetailBean);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}

	@Override
	public List<OrderMealDetailBean> getAllOrderMealDetail() {
		List<OrderMealDetailBean> result = orderMealDetailRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		}
		
		return null;
	}

	@Override
	public List<OrderMealDetailBean> getOrderMealDetailByOrderid(String orderid) {
		List<OrderMealDetailBean> result = orderMealDetailRepository.findByOrderid(orderid);
		
		
		if (!result.isEmpty()) {
			return result;
		}
		
		return null;
	}

	@Override
	public List<OrderMealDetailBean> getOrderMealDetailByMealid(String mealid) {	
		List<OrderMealDetailBean> result = orderMealDetailRepository.findByOrderid(mealid);

		if (!result.isEmpty()) {
			return result;
		}
		
		return null;
	}

}
