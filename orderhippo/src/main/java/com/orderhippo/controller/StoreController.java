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

@Api(tags = "店家資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class StoreController {
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢店家資料")
	@GetMapping("/{requestID}/stores")
	public Object getAllStores(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<UserInfoBean> userinfo = userInfoService.getUserInfofindByUserid(requestID);
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(userinfo, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			return storeInfoService.getAllStoreInfo();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation("查詢店家開店狀態")
	@GetMapping("/storestatus")
	public Object getStoreOpenStatus() {
			return storeInfoService.getAllStoreInfo().get(0).getStoreopenstatus();
	}
	
	@ApiOperation("查詢店家經緯度")
	@GetMapping("/storelocation")
	public Object getStoreLocation() {
			return storeInfoService.getAllStoreInfo().get(0).getStorelocation();
	}
	
	@ApiOperation("新增店家資料")
	@PostMapping("/stores")
	public Object addNewStore(
			@RequestBody @ApiParam(name ="店家資料", value = "需要欄位資料：STORE_NAME, STORE_ADDRESS, "
			+ "STORE_PHONE, STORE_MAIL, STORE_LOCATION, CREATE_ID") 
			StoreInfoBean storeInfoBean) {
		if (storeInfoBean != null) {
			return storeInfoService.addStoreInfo(storeInfoBean);
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}
	
//	@ApiOperation("更新店家資料")
//	@ApiResponses({
//		@ApiResponse(code = 401, message = "沒有權限"),
//		@ApiResponse(code = 404, message = "找不到路徑")
//	})
//	@PutMapping("/stores/{reviseId}/{storeId}")
//	public Object updateStoreInfo(@PathVariable String reviseId, @PathVariable String storeId, @RequestBody StoreInfoBean storeInfoBean) {
//		if ((storeInfoBean != null) && ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) && (!storeId.equals(""))) {
//			return storeInfoService.updateStoreInfo(reviseId, storeId, storeInfoBean);
//		} else {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	@ApiOperation("更新店家資料")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@PutMapping("/{requestID}/stores")
	public Object updateStoreInfo(
			@PathVariable String requestID, 
			@RequestParam(name = "token", required = true) String realHashToken,
			@RequestBody StoreInfoBean storeInfoBean) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			if (storeInfoBean != null) {
				return storeInfoService.updateStoreInfo(requestID, storeInfoBean);
			} else {
				return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
		
//		if (storeInfoBean == null) {
//			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
//		} else {
//			String storeId = storeInfoBean.getStoreid();
//			
//			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
//				return storeInfoService.updateStoreInfo(reviseId, storeInfoBean);
//			} else {
//				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or StoreId", HttpStatus.BAD_REQUEST);
//			}
//		}

//		String storeId = storeInfoBean.getStoreid();
//		
//		if ((storeInfoBean != null) && ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) ) {
//			return storeInfoService.updateStoreInfo(reviseId, storeInfoBean);
//		} else {
//			return false;
//		}
	}
}
