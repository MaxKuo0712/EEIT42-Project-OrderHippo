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
import com.orderhippo.service.service.StoreInfoService;
import com.orderhippo.service.service.viewService.VSaleCategoryService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "各類別售出圓餅圖API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VSaleCategoryController {
	
	@Autowired
	private VSaleCategoryService vSaleCategoryService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢全部各類別售出圓餅圖")
	@GetMapping(path = "/{requestID}/vsalecategory")
	public Object getAllVSaleCategorys(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			return vSaleCategoryService.getAllSaleCategory();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
