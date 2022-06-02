package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VPaymentDetailBean;
import com.orderhippo.repository.viewRepository.VPaymentDetailRepository;
import com.orderhippo.service.service.viewService.VPaymentDetailService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class VPaymentDetailServicelmp implements VPaymentDetailService {
	
//	@Autowired
//	VPaymentDetailRepository vPaymentDetailRepository;
//
//	@Override
//	public List<VPaymentDetailBean> findByOrderid(String orderid) {
//		List<VPaymentDetailBean> result = vPaymentDetailRepository.findByOrderid(orderid);
//		
//		if (result != null) {
//			return result;
//		} else {
//			return null;
//		}
//	}

}
