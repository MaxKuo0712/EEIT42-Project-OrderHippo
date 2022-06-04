package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.StoreCouponBean;

public interface StoreCouponService {
	// 查詢所有優惠卷
	public List<StoreCouponBean> getAllCoupons();
			
	// 新增單筆優惠卷
	public Object addStoreCoupon(StoreCouponBean storeCouponBean);
	
	// 查詢單筆優惠卷 By CouponID
	public List<StoreCouponBean> getStoreCouponByCouponId(String couponid);
	
	// 查詢單筆優惠卷 By StoreID
	public List<StoreCouponBean> getStoreCouponByStoreId(String storeid);
	
	// 修改優惠卷資料
	public Object updateStoreCoupon(String requestID, StoreCouponBean storeCouponBean);
	
	// By CouponName 搜尋
	public Object getStoreCouponByCouponName(String couponname);
	
	// 刪除單筆優惠卷
//	public Object deleteCoupon(String requestID, StoreCouponBean storeCouponBean);
	public Object deleteCoupon(String requestID, String couponId);
	
}
