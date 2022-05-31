package com.orderhippo.service.servicelmp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.PaymentBean;
import com.orderhippo.repository.PaymentRepository;
import com.orderhippo.service.service.PaymentService;


@Service
public class PaymentServiceImp implements PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	
	// 新增單筆付款資訊
	@Override
	public Object addPayment(PaymentBean paymentBean) {
		if (paymentBean != null) {
			try {
//				PaymentBean result = paymentRepository.save(paymentBean);
//				return new ResponseEntity<Boolean>(paymentRepository.existsById(result.getId()), HttpStatus.OK);
				paymentRepository.save(paymentBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input not found", HttpStatus.NOT_FOUND);
	}
	
	// 查詢所有付款資訊
	@Override
	public List<PaymentBean> getAllPayments() {
		List<PaymentBean> result = paymentRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By PaymentID
	@Override
	public List<PaymentBean> getPaymentByPaymentId(String paymentid) {
		List<PaymentBean> result = paymentRepository.findByPaymentid(paymentid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By OrdersID
	@Override
	public List<PaymentBean> getPaymentByOrderId(String orderid) {
		List<PaymentBean> result = paymentRepository.findByOrderid(orderid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By UserID
	@Override
	public List<PaymentBean> getPaymentByUserId(String userid) {
		List<PaymentBean> result = paymentRepository.findByUserid(userid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By StoreID
	@Override
	public List<PaymentBean> getPaymentByStoreId(String storeid) {
		List<PaymentBean> result = paymentRepository.findByStoreid(storeid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By PaymentStatus
	@Override
	public List<PaymentBean> getPaymentByPaymentstatus(Boolean paymentstatus) {
		List<PaymentBean> result = paymentRepository.findByPaymentstatus(paymentstatus);
		if(!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By StroeID + PaymentStatus
	@Override
	public List<PaymentBean> getPaymentByStoreidAndPaymentstatus(String storeid, String paymentstatus) {
		List<PaymentBean> result = paymentRepository.findByStoreidAndPaymentstatus(storeid, paymentstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 查詢單筆付款資訊 By OrdersID + PaymentStatus
	@Override
	public List<PaymentBean> getPaymentByOrderidAndPaymentstatus(String orderid, String paymentstatus) {
		List<PaymentBean> result = paymentRepository.findByOrderidAndPaymentstatus(orderid, paymentstatus);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
	
	// 修改單筆付款資料
	public Object updatePayment(String requestID, PaymentBean paymentBean) {
		List<PaymentBean> payment = paymentRepository.findByPaymentid(paymentBean.getPaymentid());
		
		if(payment.size() == 1) {
			PaymentBean currentPayments = payment.get(0);

			paymentBean.setId(currentPayments.getId());
			paymentBean.setOrderid(currentPayments.getOrderid());
			paymentBean.setUserid(currentPayments.getUserid());
			paymentBean.setStoreid(currentPayments.getStoreid());
			paymentBean.setReviseid(requestID);
			paymentBean.setRevisetime(new Date());
			
			try {
				paymentRepository.save(paymentBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：PaymentID", HttpStatus.NOT_FOUND);
	}
}
