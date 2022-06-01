package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VOrderStatusCountBean;
import com.orderhippo.repository.viewRepository.VOrderStatusCountRepository;
import com.orderhippo.service.service.viewService.VOrderStatusCountService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class VOrderStatusCountServiceImp implements VOrderStatusCountService {
	
	@Autowired
	private VOrderStatusCountRepository vOrderStatusCountRepository;
	
	// 查詢所有 OrderStatusCount
	@Override
	public List<VOrderStatusCountBean> getAllOrderStatusCount() {
		List<VOrderStatusCountBean> result = vOrderStatusCountRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
	
}
