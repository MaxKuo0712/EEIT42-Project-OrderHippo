package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.PaymentBean;

public interface PaymentService {
	// 新增單筆付款資訊
	public Object addPayment(PaymentBean paymentBean);
	
	// 查詢所有付款資訊
	public List<PaymentBean> getAllPayments();
	
	// 查詢單筆付款資訊 By PaymentID
	public List<PaymentBean> getPaymentByPaymentId(String paymentid);
	
	// 查詢單筆付款資訊 By OrdersID
	public List<PaymentBean> getPaymentByOrderId(String orderid);
	
	// 查詢單筆付款資訊 By UserID
	public List<PaymentBean> getPaymentByUserId(String userid);
	
	// 查詢單筆付款資訊 By StoreID
	public List<PaymentBean> getPaymentByStoreId(String storeid);
	
	// 查詢單筆付款資訊 By PaymentStatus
	public List<PaymentBean> getPaymentByPaymentstatus(Boolean paymentstatus);
	
	// By StroeID + PaymentStatus
	public List<PaymentBean> getPaymentByStoreidAndPaymentstatus(String storeid, String paymentstatus);
	
	// By OrdersID + PaymentStatus
	public List<PaymentBean> getPaymentByOrderidAndPaymentstatus(String orderid, String paymentstatus);
	
	// 修改單筆付款資料
	public Object updatePayment(String requestID, PaymentBean paymentBean);
}
