package com.orderhippo.repository.viewRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.viewBean.VReviseMealDisplayBean;

public interface VReviseMealDisplayRepository extends JpaRepository<VReviseMealDisplayBean, String>{
	
	List<VReviseMealDisplayBean> findByMealid(String mealid);
}
