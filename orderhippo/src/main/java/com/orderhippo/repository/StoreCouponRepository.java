package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.StoreCouponBean;

public interface StoreCouponRepository extends JpaRepository<StoreCouponBean, Integer>{
	// By CouponID 搜尋
	List<StoreCouponBean> findByCouponid(String couponid);
	
	// By StoreID 搜尋
	List<StoreCouponBean> findByStoreid(String storeid);
	
	// By CouponName 搜尋
	List<StoreCouponBean> findByCouponname(String couponname);
}
