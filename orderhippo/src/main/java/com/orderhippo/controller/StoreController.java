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
import com.orderhippo.service.StoreInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "店家資訊API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class StoreController {
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢所有店家資料")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/stores")
	public List<StoreInfoBean> getAllStores() {
		return storeInfoService.getAllStoreInfo();
	}
	
	@ApiOperation("新增店家資料")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@PostMapping("/stores")
	public boolean addNewStore(
			@RequestBody @ApiParam(name ="店家資料", value = "需要欄位資料：STORE_NAME, STORE_ADDRESS, "
			+ "STORE_PHONE, STORE_MAIL, STORE_LOCATION, CREATE_ID") 
			StoreInfoBean storeInfoBean) {
		if (storeInfoBean != null) {
			return storeInfoService.addStoreInfo(storeInfoBean);
		}
		return false;
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
	@PutMapping("/stores/{reviseId}/{storeId}")
	public boolean updateStoreInfo(@PathVariable String reviseId, @PathVariable String storeId, @RequestBody StoreInfoBean storeInfoBean) {
		if ((storeInfoBean != null) && ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) 
				&& (storeId != null && storeId.trim().length() != 0)) {
			return storeInfoService.updateStoreInfo(reviseId, storeId, storeInfoBean);
		} else {
			return false;
		}
	}
}
