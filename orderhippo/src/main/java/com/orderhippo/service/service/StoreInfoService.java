package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.StoreInfoBean;

public interface StoreInfoService {
	// 新增單筆店家資料
    public Object addStoreInfo(StoreInfoBean storeInfoBean);
    
    // 取得全部店家資料
    public List<StoreInfoBean> getAllStoreInfo();
    
    // 取得單筆店家資料 by storeID
    public List<StoreInfoBean> getStoreInfoByStoreid(String storeid);
    
    // 更新單筆店家資料
    public Object updateStoreInfo(String requestID, StoreInfoBean storeInfoBean);
}
