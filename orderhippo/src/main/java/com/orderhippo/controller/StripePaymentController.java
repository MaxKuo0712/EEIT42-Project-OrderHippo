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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.model.viewBean.VPaymentDetailBean;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.service.service.viewService.VPaymentDetailService;
import com.orderhippo.utils.ProjectUtils;
import com.stripe.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import io.swagger.annotations.Api;

@Api(tags = "金流Stripe付款API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class StripePaymentController {

	@Autowired
	UserInfoService userInfoService;

	@PostMapping("/{requestID}/stripepayment")
	public Object getAllStripePayment(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody VPaymentDetailBean vPaymentDetail) throws StripeException {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, null, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);

		if(verifyResult) {
			// We initilize stripe object with the api key
			init();
			// We create a  stripe session parameters
			SessionCreateParams params = SessionCreateParams.builder()
					// We will use the credit card payment method 
					.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
					.setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl("http://localhost:5500/%E5%89%8D%E7%AB%AF%E8%B3%87%E6%96%99/bigproj202206081/thanks.html")
					.setCancelUrl("http://localhost:5500/%E5%89%8D%E7%AB%AF%E8%B3%87%E6%96%99/bigproj202206081/cancel.html")
					.addLineItem(
							SessionCreateParams.LineItem.builder().setQuantity(Long.parseLong("1"))
									.setPriceData(
											SessionCreateParams.LineItem.PriceData.builder()
													.setCurrency("TWD").setUnitAmount((Long.parseLong(vPaymentDetail.getMealprice().toString()) * 100))
													.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
															.builder()
															.setName("OrderHippo Payment")
															.setDescription(vPaymentDetail.getMeals().toString())
															.build())
													.build())
									.build())
					.build();
			// create a stripe session
			Session session = Session.create(params);
			Map<String, String> responseData = new HashMap<>();
			// We get the sessionId and we putted inside the response data you can get more info from the session object
			responseData.put("id", session.getId());
			// We can return only the sessionId as a String
			return session.toJson();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}

	private static void init() {
		Stripe.apiKey = "sk_test_51L0jIYBaXhvcETm2CmBbKDiwlI9mdnEyLBSPfm8aysNxPnpEpR9FyhMA1aFWfy9Dv9F55NFF4ztioxbiNC0ewA7P00jbnaA0IQ";
	}

}
