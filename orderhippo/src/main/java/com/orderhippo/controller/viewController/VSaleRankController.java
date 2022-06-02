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
import com.orderhippo.service.service.viewService.VSaleRankService;
import com.orderhippo.utils.ProjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "銷售前10名API")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VSaleRankController {
	
	@Autowired
	private VSaleRankService vSaleRankService;
	
	@Autowired
	private StoreInfoService storeInfoService;
	
	@ApiOperation("查詢全部銷售前10名商品")
	@GetMapping(path = "/{requestID}/vsalerank")
	public Object getAllVSaleRanks(
			@PathVariable String requestID,
			@RequestParam(name = "token", required = true) String realHashToken) {
		
		List<StoreInfoBean> storeinfo = storeInfoService.getStoreInfoByStoreid(requestID);
		
		String dbToken = ProjectUtils.getDBToken(null, storeinfo, requestID);
		boolean verifyResult = ProjectUtils.verifyToken(realHashToken, dbToken);
		
		if (verifyResult) {
			return vSaleRankService.getAllSaleRank();
		} else {
			return new ResponseEntity<String>("權限不足", HttpStatus.BAD_REQUEST);
		}
	}
	
}
