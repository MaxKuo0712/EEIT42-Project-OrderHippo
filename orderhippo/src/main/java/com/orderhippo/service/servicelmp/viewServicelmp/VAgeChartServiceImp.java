package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VAgeChartBean;
import com.orderhippo.repository.viewRepository.VAgeChartRepository;
import com.orderhippo.service.service.viewService.VAgeChartService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VAgeChartServiceImp implements VAgeChartService{
	
	@Autowired
	private VAgeChartRepository vAgeChartRepository;
	
	// 查詢所有 AgeChart
	public List<VAgeChartBean> getAllAgeChart() {
		List<VAgeChartBean> result = vAgeChartRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
