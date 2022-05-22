package com.orderhippo.service;

import java.util.List;

import com.orderhippo.model.StoreInfoBean;

public interface StoreInfoService {
	// 新增單筆店家資料
    public boolean addStoreInfo(StoreInfoBean storeInfoBean);
    
    // 取得全部店家資料
    public List<StoreInfoBean> getAllStoreInfo();
    
    // 更新單筆店家資料
    public boolean updateStoreInfo(String reviseid, StoreInfoBean storeInfoBean);
}
