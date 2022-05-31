package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.StoreMessageBean;

public interface StoreMessageRepository extends JpaRepository<StoreMessageBean, Integer>{
	// By MessageID 搜尋
	List<StoreMessageBean> findByMessageid(String messageid);
	
	// By StoreID 搜尋
	List<StoreMessageBean> findByStoreid(String storeid);
}
