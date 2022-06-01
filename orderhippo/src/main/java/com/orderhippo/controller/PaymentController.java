package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.PaymentBean;
import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.PaymentService;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="付款資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	// 新增單筆付款資料
	@ApiOperation("新增單筆付款資料")
	@PostMapping(path="/{requestID}/payment")
	public Object addPayment(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestBody PaymentBean paymentBean) {
			
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, null, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(paymentBean != null) {
				paymentBean.setCreateid(requestID);
				return paymentService.addPayment(paymentBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 查詢付款資料 by PaymentID 或 OrdersID 或 UserID 或 StoreID 或 PaymentStatus 或 ALL
	@ApiOperation("查詢付款資料 by PaymentID 或 OrdersID 或 UserID 或 StoreID 或 PaymentStatus 或 ALL")
	@GetMapping(path="/{requestID}/payments")
	public Object getPayments(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name="orderid", required=false) String orderId,
			@RequestParam(name="userid", required=false) String userId,
			@RequestParam(name="storeid", required=false) String storeId,
			@RequestParam(name="paymentid", required=false) String paymentId) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(orderId != null && orderId.trim().length() != 0) {
				return paymentService.getPaymentByOrderId(orderId);
			} else if(userId != null && userId.trim().length() != 0) {
				return paymentService.getPaymentByUserId(userId);
			} else if(storeId != null && storeId.trim().length() != 0) {
				return paymentService.getPaymentByStoreId(storeId);
			} else if(paymentId != null && paymentId.trim().length() != 0) {
				return paymentService.getPaymentByPaymentId(paymentId);
			} else {
				return paymentService.getAllPayments();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 修改付款資料 PutMapping
	@ApiOperation("更新付款資料")
	@PutMapping(path="/{requestID}/payment", consumes="application/json")
	public Object patchPayment(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestBody PaymentBean paymentBean) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if(paymentBean != null) {
				return paymentService.updatePayment(requestID, paymentBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		} 
	}
}
