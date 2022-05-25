package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@Api("提供TokenAPI")
@RestController
@RequestMapping("/api")
public class CreateTokenController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("Token")
	@GetMapping("/getToken/{requestID}")
	public Object getToken(@PathVariable String requestID) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		StoreInfoBean storeinfo = storeInfoService.getAllStoreInfo().get(0);

		if ((userinfo.size() == 1 && userinfo.get(0).getUserid().equals(requestID))) {
			String token = ProjectUtils.createToken(requestID);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} else if ((storeinfo.getStoreid().equals(requestID))) {
			String token = ProjectUtils.createToken(requestID);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}

		return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
	}
}
