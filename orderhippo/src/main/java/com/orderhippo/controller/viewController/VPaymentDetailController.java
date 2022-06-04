package com.orderhippo.controller.viewController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.service.service.viewService.VPaymentDetailService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;

@Api(tags = "更改菜單顯示API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VPaymentDetailController {
	
	@Autowired
	VPaymentDetailService vPaymentDetailService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	StoreInfoService storeInfoService;
	
//	@GetMapping("/{requestID}/vpaymentdetail")
//	public Object getAllVPaymentDetailService (
//			@PathVariable String requestID,
//			@RequestParam(name = "token", required = true) String realHashToken,
//			@RequestParam(name = "orderid", required = false) String orderId) {
//		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
//		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
//		
//		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
//		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
//		
//		if(verifyResult) {
//			return vPaymentDetailService.findByOrderid(orderId);
//		} else {
//			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
//		}
//	}
}
