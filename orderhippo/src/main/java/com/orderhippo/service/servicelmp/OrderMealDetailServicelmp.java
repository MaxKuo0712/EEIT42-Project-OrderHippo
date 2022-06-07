package com.orderhippo.service.servicelmp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.MealBean;
import com.orderhippo.model.OrderMealDetailBean;
import com.orderhippo.repository.OrderMealDetailRepository;
import com.orderhippo.service.service.OrderMealDetailService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class OrderMealDetailServicelmp implements OrderMealDetailService {
	
	@Autowired
	private OrderMealDetailRepository orderMealDetailRepository;

	@Override
	public Object addOrderMealDetail(List<OrderMealDetailBean> ListOrderMealDetailBean) {
		
		if (ListOrderMealDetailBean.size() > 0) {
			try {
				orderMealDetailRepository.saveAll(ListOrderMealDetailBean);
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

//	@Override
//	public Object updateOrderMealDetail(String reviseId, OrderMealDetailBean orderMealDetailBean) {
//		List<OrderMealDetailBean> orderMealDetail = orderMealDetailRepository.findByOrderid(orderMealDetailBean.getOrderid());
//		
//		if (orderMealDetail.size() == 1) {
//			OrderMealDetailBean currentMeal = orderMealDetail.get(0);
//			
//			orderMealDetailBean.setId(currentMeal.getId());
//			orderMealDetailBean.setReviseid(reviseId);
//			orderMealDetailBean.setRevisetime(new Date());
//			
//			try {
//				orderMealDetailRepository.save(orderMealDetailBean);
//				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
//		return new ResponseEntity<String>("資料不存在：MealID", HttpStatus.NOT_FOUND);
//	}
}
