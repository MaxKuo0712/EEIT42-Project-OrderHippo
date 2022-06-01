package com.orderhippo.service.service.viewService;

import java.util.List;

import com.orderhippo.model.viewBean.VOrderStatusCountBean;

public interface VOrderStatusCountService {
	
	// 查詢所有 OrderStatusCount
	public List<VOrderStatusCountBean> getAllOrderStatusCount();
}
