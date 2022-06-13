package com.orderhippo.repository.viewRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.viewBean.VOrderDisplayBean;

public interface VOrderDisplayRepository extends JpaRepository<VOrderDisplayBean, String>{
	
	List<VOrderDisplayBean> findByOrderstatus(String orderstatus);
	
	// By MEAL_CATEGORY_ID 搜尋
	List<VOrderDisplayBean> findByUseridAndOrderstatus(String userid, String orderstatus);
}
