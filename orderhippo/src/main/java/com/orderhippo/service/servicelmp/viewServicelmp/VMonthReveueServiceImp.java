package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VMonthReveueBean;
import com.orderhippo.repository.viewRepository.VMonthReveueRepository;
import com.orderhippo.service.service.viewService.VMonthReveueService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VMonthReveueServiceImp implements VMonthReveueService{
	
	@Autowired
	private VMonthReveueRepository vMonthReveueRepository;
	
	// 查詢所有 MonthReveue
	@Override
	public List<VMonthReveueBean> getAllMonthReveue() {
		List<VMonthReveueBean> result = vMonthReveueRepository.findAll();
		
		if(!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
