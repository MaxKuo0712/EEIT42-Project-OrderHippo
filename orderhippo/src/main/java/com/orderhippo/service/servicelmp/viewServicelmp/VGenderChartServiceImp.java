package com.orderhippo.service.servicelmp.viewServicelmp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.viewBean.VGenderChartBean;
import com.orderhippo.repository.viewRepository.VGenderChartRepository;
import com.orderhippo.service.service.viewService.VGenderChartService;

@Service
@Transactional(rollbackFor = SQLException.class)

public class VGenderChartServiceImp implements VGenderChartService {
	
	@Autowired
	private VGenderChartRepository vGenderChartRepository;
	
	// 查詢所有性別數量
	public List<VGenderChartBean> getAllGenderChart() {
		List<VGenderChartBean> result = vGenderChartRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
