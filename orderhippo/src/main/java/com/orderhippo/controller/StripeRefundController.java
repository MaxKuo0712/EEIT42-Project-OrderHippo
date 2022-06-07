package com.orderhippo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;

import io.swagger.annotations.Api;

@Api(tags = "金流Stripe退款API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class StripeRefundController {
	
	@Autowired
	private UserInfoService userinfoservice;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	// 新增單筆退款 Create a refund
	@PostMapping("/{requestID}/striperefund")
	public Object addStripeRefund(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name = "paymentintent", required = true) String paymentIntent) throws StripeException {
		
		List<UserInfoBean> userinfo = userinfoservice.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);

		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			init();
			Map<String, Object> params = new HashMap<>();
			
			params.put(
				"payment_intent", 
				paymentIntent
			);
			
			Refund refund = Refund.create(params);
			
			return refund.toJson();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// api token 金鑰匙
	private static void init() {
		Stripe.apiKey = "sk_test_51L0jIYBaXhvcETm2CmBbKDiwlI9mdnEyLBSPfm8aysNxPnpEpR9FyhMA1aFWfy9Dv9F55NFF4ztioxbiNC0ewA7P00jbnaA0IQ";
	}
	
}
