package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.StoreMessageBean;

public interface StoreMessageService {
	// 查詢所有店家訊息
	public List<StoreMessageBean> getAllmessages();
	
	// 新增單筆店家訊息
	public Object addStoreMessage(StoreMessageBean storeMessageBean);
	
	// 查詢單筆店家訊息 By MessageID 
	public List<StoreMessageBean> getStoreMessageByMessageId(String messageid);
	
	// 查詢單筆店家訊息 By StoreID
	public List<StoreMessageBean> getStoreMessageByStoreId(String storeid);
	
	// 修改店家訊息
	public Object updateStoreMessage(String requestID, StoreMessageBean storeMessageBean);
	
	// 刪除單筆店家訊息
	public Object deleteMessage(String requestID, String messageId);
}
