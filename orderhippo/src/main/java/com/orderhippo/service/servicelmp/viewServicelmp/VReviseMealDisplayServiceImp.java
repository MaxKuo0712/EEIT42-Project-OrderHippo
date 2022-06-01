package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VReviseMealDisplayBean;
import com.orderhippo.repository.viewRepository.VReviseMealDisplayRepository;
import com.orderhippo.service.service.viewService.VReviseMealDisplayService;

@Service
@Transactional(rollbackFor = SQLException.class)   // rollback => transaction 退回來，不做transaction

public class VReviseMealDisplayServiceImp implements VReviseMealDisplayService{
	
	@Autowired
	private VReviseMealDisplayRepository vReviseMealDisplayRepository;
	
	// 查詢所有 ReviseMealDisplay
	@Override
	public List<VReviseMealDisplayBean> getAllReviseMealDisplay() {
		List<VReviseMealDisplayBean> result = vReviseMealDisplayRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<VReviseMealDisplayBean> findByMealid(String mealid) {
		List<VReviseMealDisplayBean> result = vReviseMealDisplayRepository.findByMealid(mealid);
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

}
