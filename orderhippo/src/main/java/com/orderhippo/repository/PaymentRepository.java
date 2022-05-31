package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.PaymentBean;

public interface PaymentRepository extends JpaRepository<PaymentBean, Integer>{

	// By PaymentID 搜尋
	List<PaymentBean> findByPaymentid(String paymentid);
	
	// By OrdersID 搜尋
	List<PaymentBean> findByOrderid(String orderid);
	
	// By UserID 搜尋
	List<PaymentBean> findByUserid(String userid);
	
	// By StoreID 搜尋
	List<PaymentBean> findByStoreid(String storeid);
	
	// By PaymentStatus
	List<PaymentBean> findByPaymentstatus(Boolean paymentstatus);
	
	// By StroeID + PaymentStatus 搜尋
	List<PaymentBean> findByStoreidAndPaymentstatus(String storeid, String paymentstatus);
	
	// By OrdersID + PaymentStatus 搜尋
	List<PaymentBean> findByOrderidAndPaymentstatus(String orderid, String paymentstatus);
	
}
