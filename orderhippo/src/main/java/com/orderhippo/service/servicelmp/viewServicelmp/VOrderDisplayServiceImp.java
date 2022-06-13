package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VOrderDisplayBean;
import com.orderhippo.repository.viewRepository.VOrderDisplayRepository;
import com.orderhippo.service.service.viewService.VOrderDisplayService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VOrderDisplayServiceImp implements VOrderDisplayService {
	
	@Autowired
	private VOrderDisplayRepository vOrderDisplayRepository;
	
	// 查詢所有 OrderDisplay
	@Override
	public List<VOrderDisplayBean> getAllOrderDisplay() {
		List<VOrderDisplayBean> result = vOrderDisplayRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<VOrderDisplayBean> getByOrderstatus(String orderStatus) {
		List<VOrderDisplayBean> result = vOrderDisplayRepository.findByOrderstatus(orderStatus);
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<VOrderDisplayBean> getByUserIDAndOrderStatus(String userid, String orderstatus) {
		List<VOrderDisplayBean> result = vOrderDisplayRepository.findByUseridAndOrderstatus(userid, orderstatus);
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
