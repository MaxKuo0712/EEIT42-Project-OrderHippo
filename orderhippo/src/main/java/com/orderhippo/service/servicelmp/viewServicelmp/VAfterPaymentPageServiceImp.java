package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VAfterPaymentPageBean;
import com.orderhippo.repository.viewRepository.VAfterPaymentPageRepository;
import com.orderhippo.service.service.viewService.VAfterPaymentPageService;


@Service
@Transactional(rollbackFor = SQLException.class)

public class VAfterPaymentPageServiceImp implements VAfterPaymentPageService {
	
	@Autowired
	private VAfterPaymentPageRepository vAfterPaymentPageRepository; 
	
	// 查詢訂單結帳後的確認頁 by OrdersID
	public List<VAfterPaymentPageBean> getAfterPaymentPageByOrderId(String orderid) {
		List<VAfterPaymentPageBean> result = vAfterPaymentPageRepository.findByOrderid(orderid);
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
	
}
