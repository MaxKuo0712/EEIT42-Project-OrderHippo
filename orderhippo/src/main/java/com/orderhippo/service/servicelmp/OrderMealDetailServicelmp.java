package com.orderhippo.service.servicelmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.OrderMealDetailBean;
import com.orderhippo.repository.OrderMealDetailRepository;
import com.orderhippo.service.service.OrderMealDetailService;

@Service
public class OrderMealDetailServicelmp implements OrderMealDetailService {
	
	@Autowired
	private OrderMealDetailRepository orderMealDetailRepository;

	@Override
	public Object addOrderMealDetail(OrderMealDetailBean orderMealDetailBean) {
		
		if (orderMealDetailBean != null) {
			try {
				orderMealDetailRepository.save(orderMealDetailBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
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