package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.StoreInfoBean;

public interface StoreInfoRepository extends JpaRepository<StoreInfoBean, Integer> {
	// by StoreID查詢
	List<StoreInfoBean> findByStoreid(String storeid);
	
	// by StoreMail查詢
	List<StoreInfoBean> findByStoremail(String storemail);
}
