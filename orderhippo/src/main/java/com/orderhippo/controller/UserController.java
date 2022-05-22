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

import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "使用者資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class UserController {
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@ApiOperation("查詢使用者資料")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/users")
	public List<UserInfoBean> getUserInfo(			
			@RequestParam(name = "userid", required = false) String userid,
			@RequestParam(name = "usermail", required = false) String usermail) {
		if (userid != null && userid.trim().length() != 0) {
			return userInfoService.getUserInfofindByUserid(userid);
		} else if (usermail != null && usermail.trim().length() != 0) {
			return userInfoService.getUserInfofindfindByUsermail(usermail);
		} else {
			return userInfoService.getAllUserInfo();
		}
	}
	
	@ApiOperation("新增使用者資料")
	@PostMapping("/users")
	public boolean addNewUser(
			@RequestBody @ApiParam(name ="使用者資料", value = "需要欄位資料：USER_ID, USER_NAME, "
			+ "USER_GENDER, USER_PHONE, USER_MAIL, USER_BIRTH, USER_ADDRESS, CREATE_ID") 
			UserInfoBean userInfoBean) {
		if (userInfoBean != null) {
			return userInfoService.addUserInfo(userInfoBean);
		}
		return false;
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
	@PutMapping("/users/{reviseId}")
	public boolean updateLoginTime(@PathVariable String reviseId, @RequestBody UserInfoBean userInfoBean) {
		String userId = userInfoBean.getUserid();
		if ((userInfoBean != null) && ((reviseId.equals(userId)) || (reviseId.equals("Admin")))) {
			return userInfoService.updateUserInfo(reviseId, userInfoBean);	
		} else {
			return false;
		}
	}
}
