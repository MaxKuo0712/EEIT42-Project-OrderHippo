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

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "使用者資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class UserController {
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢使用者資料")
	@GetMapping("/{requestID}/users")
	public Object getUserInfo(
			@PathVariable String requestID,
			@RequestParam(name = "userid", required = false) String userid,
			@RequestParam(name = "usermail", required = false) String usermail,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (userid != null && userid.trim().length() != 0) {
				return userInfoService.getUserInfofindByUserid(userid);
			} else if (usermail != null && usermail.trim().length() != 0) {
				return userInfoService.getUserInfofindfindByUsermail(usermail); 
			} else {
				return userInfoService.getAllUserInfo();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation("新增使用者資料")
	@PostMapping("/users")
	public Object addNewUser(@RequestBody UserInfoBean userInfoBean) {
		if (userInfoBean != null) {
			return userInfoService.addUserInfo(userInfoBean);
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

//	@ApiOperation("更新使用者資料")
//	@PutMapping("/users/{reviseId}/{userId}")
//	public Object updateLoginTime(@PathVariable String reviseId, @PathVariable String userId, @RequestBody UserInfoBean userInfoBean) {
//		if ((userInfoBean != null) && ((reviseId.equals(userId)) || (reviseId.equals("Admin"))) && (!userId.equals(""))) {
//			return userInfoService.updateUserInfo(reviseId, userId, userInfoBean);	
//		} else {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@ApiOperation("更新使用者資料")
	@PutMapping("/{requestID}/users")
	public Object updateLoginTime(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody UserInfoBean userInfoBean) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (userInfoBean != null) {
				return userInfoService.updateUserInfo(requestID, userInfoBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
		
		
//		if (userInfoBean == null) {
//			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//		} else {
//			String storeId = userInfoBean.getUserid();
//			
//			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
//				return userInfoService.updateUserInfo(reviseId, userInfoBean);
//			} else {
//				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or UserId", HttpStatus.BAD_REQUEST);
//			}
//		}
		
//		String userId = userInfoBean.getUserid();
//		
//		if ((userInfoBean != null) && ((reviseId.equals(userId)) || (reviseId.equals("Admin")))) {
//			return userInfoService.updateUserInfo(reviseId, userInfoBean);	
//		} else {
//			return false;
//		}
	}
}
