package com.orderhippo.service.service.viewService;

import java.util.List;

import com.orderhippo.model.viewBean.VReviseMealDisplayBean;

public interface VReviseMealDisplayService {
	
	// 查詢所有 ReviseMealDisplay
	public List<VReviseMealDisplayBean> getAllReviseMealDisplay();
	
	//查詢 BY Meal ID
	public List<VReviseMealDisplayBean> findByMealid(String mealid);
}
