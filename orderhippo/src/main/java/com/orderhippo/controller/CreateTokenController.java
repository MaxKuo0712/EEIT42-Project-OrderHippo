package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "提供TokenAPI")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CreateTokenController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("Token")
	@GetMapping("/getToken/{requestID}")
	public Object getToken(@PathVariable String requestID) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);

		if (userinfo != null) {
			if (userinfo.get(0).getUserid().equals(requestID)) {
				String token = ProjectUtils.createToken(requestID);
				String saveToken = ProjectUtils.createToken(token);
				
				UserInfoBean saveTarget = userinfo.get(0);
				saveTarget.setUsertoken(saveToken);
				userInfoService.updateUserInfo(requestID, saveTarget);
				
				return new ResponseEntity<String>(token, HttpStatus.OK);
			}
		} else if (storeinfo != null) {
			if (storeinfo.get(0).getStoreid().equals(requestID)) {
				String token = ProjectUtils.createToken(requestID);
				String saveToken = ProjectUtils.createToken(token);
				
				StoreInfoBean saveTarget = storeinfo.get(0);
				saveTarget.setStoretoken(saveToken);
				storeInfoService.updateStoreInfo(requestID, saveTarget);
				
				return new ResponseEntity<String>(token, HttpStatus.OK);	
			}
		} else {
			return new ResponseEntity<String>("使用者不存在 / 權限不足", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("使用者不存在 / 權限不足", HttpStatus.BAD_REQUEST);
	}
}
