package com.orderhippo.service.service.viewService;

import java.util.List;

import com.orderhippo.model.viewBean.VAfterPaymentPageBean;

public interface VAfterPaymentPageService {
	
	// 查詢訂單結帳後的確認頁 By OrdersID
	public List<VAfterPaymentPageBean> getAfterPaymentPageByOrderId(String orderid);
	
}
