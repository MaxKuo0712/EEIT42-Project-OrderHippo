package com.orderhippo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.orderhippo.model.OrderMealDetailBean;
import com.orderhippo.model.PaymentBean;
import com.orderhippo.service.service.OrderMealDetailService;
import com.orderhippo.service.service.PaymentService;
import com.stripe.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
public class TestController {
	
	// create a Gson object
		private static Gson gson = new Gson();
		
		@Autowired
		OrderMealDetailService orderMealDetailService;

		@PostMapping("/payment")
//		public String paymentWithCheckoutPage(@RequestBody OrderMealDetailBean payment) throws StripeException {
		public String paymentWithCheckoutPage(
				@RequestParam(name = "orderid", required = true) String orderId) throws StripeException {
			
			OrderMealDetailBean orderMealDetail =  orderMealDetailService.getOrderMealDetailByOrderid(orderId).get(0);
			
			// We initilize stripe object with the api key
			init();
			// We create a  stripe session parameters
			SessionCreateParams params = SessionCreateParams.builder()
					// We will use the credit card payment method 
					.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
					.setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl("http://localhost:8080/success.html")
					.setCancelUrl("http://localhost:8080/cancel.html")
					.addLineItem(
							SessionCreateParams.LineItem.builder().setQuantity(Long.parseLong(orderMealDetail.getOrdermealqty().toString()))
									.setPriceData(
											SessionCreateParams.LineItem.PriceData.builder()
													.setCurrency("TWD")
													.setUnitAmount((long) orderMealDetail.getMealprice() * 100)
													.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
															.builder().setName(orderMealDetail.getMealid()).build())
													.build())
									.build())
					.build();
	  // create a stripe session
			Session session = Session.create(params);
			Map<String, String> responseData = new HashMap<>();
	    // We get the sessionId and we putted inside the response data you can get more info from the session object
			responseData.put("id", session.getId());
	      // We can return only the sessionId as a String
			System.out.println(session);
			System.out.println(responseData);
			return session.getUrl();
		}

		private static void init() {
			Stripe.apiKey = "sk_test_51L0jIYBaXhvcETm2CmBbKDiwlI9mdnEyLBSPfm8aysNxPnpEpR9FyhMA1aFWfy9Dv9F55NFF4ztioxbiNC0ewA7P00jbnaA0IQ";
		}

}
