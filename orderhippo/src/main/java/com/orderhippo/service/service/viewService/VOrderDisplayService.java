package com.orderhippo.service.service.viewService;

import java.util.List;

import com.orderhippo.model.viewBean.VOrderDisplayBean;

public interface VOrderDisplayService {
	
	// 查詢所有 OrderDisplay
	public List<VOrderDisplayBean> getAllOrderDisplay();
	
	// 查詢 By 訂單狀態
	public List<VOrderDisplayBean> getByOrderstatus(String orderStatus);
}
