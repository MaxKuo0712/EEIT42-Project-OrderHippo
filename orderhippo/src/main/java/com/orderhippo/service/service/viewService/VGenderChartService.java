package com.orderhippo.service.service.viewService;

import java.util.List;

import com.orderhippo.model.viewBean.VGenderChartBean;

public interface VGenderChartService {
	
	// 查詢所有性別數量
	public List<VGenderChartBean> getAllGenderChart();
}
