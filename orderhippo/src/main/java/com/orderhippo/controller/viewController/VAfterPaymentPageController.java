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

import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.service.service.viewService.VAfterPaymentPageService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "訂單結帳後的確認頁API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VAfterPaymentPageController {
	@Autowired
	private VAfterPaymentPageService vAfterPaymentPageService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation("查詢訂單結帳後的確認頁")
	@GetMapping(path = "/{requestID}/vafterpaymentpage")
	public Object getAllVAfterPaymentPages(
			@PathVariable String requestID,
			@RequestParam(name = "orderid", required = true) String orderId,
			@RequestParam(name = "token", required = true) String realHashToken) {
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, null, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			return vAfterPaymentPageService.getAfterPaymentPageByOrderId(orderId);
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
}
