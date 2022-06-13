package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.StoreMessageBean;
import com.orderhippo.model.UserInfoBean;
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.StoreMessageService;
import com.orderhippo.service.service.UserInfoService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "店家廣告訊息API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class StoreMessageController {
	
	@Autowired
	private StoreMessageService storeMessageService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	// 新增單筆 Message
	@ApiOperation("新增單筆店家廣告訊息")
	@PostMapping(path="/{requestID}/message")
	public Object addMessage(
			@PathVariable String requestID,
			@RequestParam(name="token", required=true) String realHashToken,
			@RequestBody StoreMessageBean storeMessageBean) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (storeMessageBean != null) {
				storeMessageBean.setStoreid(requestID);
				storeMessageBean.setCreateid(requestID);
				return storeMessageService.addStoreMessage(storeMessageBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// 查詢Message By StoreID 或 MessageID 或 ALL
	@ApiOperation("查詢店家廣告訊息 by StoreID 或 MessageID 或 ALL")
	@GetMapping(path="/{requestID}/messages")
	public Object getMessages(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name="storeid", required=false) String storeId,
			@RequestParam(name="messageid", required=false) String messageId) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
	
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if(storeId != null && storeId.trim().length() != 0) {
				return storeMessageService.getStoreMessageByStoreId(storeId);
			} else if(messageId != null && messageId.trim().length() != 0) {
				return storeMessageService.getStoreMessageByMessageId(messageId);
			} else {
				return storeMessageService.getAllmessages();
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 查詢店家廣告訊息 ALL Open
	@ApiOperation("查詢店家廣告訊息 ALL Open")
	@GetMapping(path="/messages")
	public Object getAllOpenMessages() {
		return storeMessageService.getAllmessages();
	}
	
	
	// 修改Message 資料 PutMapping
	@ApiOperation("修改店家訊息資料")
	@PutMapping(path="/{requestID}/message", consumes="application/json")
	public Object patchMessage(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody StoreMessageBean storeMessageBean) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (storeMessageBean != null) {
				return storeMessageService.updateStoreMessage(requestID, storeMessageBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 刪除單筆店家資訊
	@ApiOperation("刪除店家廣告訊息")
	@DeleteMapping(path = "/{requestID}/message")
	public Object removeMessage(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestParam(name="messageid", required=true) String messageId) {

		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);

		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if(verifyResult) {
			if (messageId != null && messageId.trim().length() != 0) {
				return storeMessageService.deleteMessage(requestID, messageId);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}			
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}

//		if(verifyResult) {
//			var isRemoved = storeMessageService.deleteMessage(requestID, storeMessageBean);
//			if(isRemoved != null) {
//				return new ResponseEntity<Boolean>(true, HttpStatus.OK); 
//			} else {
//				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//			}
//
//		} else {
//			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
//		}
	}
}
