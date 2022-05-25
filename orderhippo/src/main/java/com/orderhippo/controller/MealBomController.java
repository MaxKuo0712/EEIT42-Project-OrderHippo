package com.orderhippo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderhippo.model.MealBomBean;
import com.orderhippo.repository.MealBomBeanRepository;
import com.orderhippo.service.service.MealBomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Meal BOM API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class MealBomController {
	
	@Autowired
	private MealBomService mealBOMService;
	
	// 新增單筆BOM
	@ApiOperation("新增單筆BOM")
	@PostMapping(path="/mealbom")
	public Object addBom(@RequestBody MealBomBean mealBomBean) {
		if (mealBomBean != null) {
			return mealBOMService.addBOM(mealBomBean);
		} else {
			return new ResponseEntity<String>("Input not found", HttpStatus.NOT_FOUND);
		}
	}
	
	// 查詢BOM by BOMId 或 MealId 或 MealName 或 IngredientName 或 ALL
	@ApiOperation("查詢BOM by BOMId 或 MealId 或 IngredientId 或 ALL")
	@GetMapping(path="/mealboms")
	public List<MealBomBean> getMealboms(
			@RequestParam(name = "mealid", required = false) String mealId, 
			@RequestParam(name = "ingredientid", required = false) String ingredientId,
			@RequestParam(name = "bomid", required = false) String bomId)
	{
		if(mealId != null && mealId.trim().length() != 0) {
			return mealBOMService.getBomByMealid(mealId);
		} else if(ingredientId != null && ingredientId.trim().length() != 0) {
			return mealBOMService.getBomByIngredientid(ingredientId);
		} else if(bomId != null && bomId.trim().length() != 0) {
			return mealBOMService.getBomByBomid(bomId);
		} else {
			return mealBOMService.getAllMealbom();
		}
	}
	
	// 修改BOM資料 Putmapping
	@ApiOperation("修改BOM資料")
	@PutMapping(path="/mealbom/{reviseId}", consumes="application/json")
	public Object patchBom(@PathVariable String reviseId, @RequestBody MealBomBean patch) {
		if(patch == null) {
			return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
		} else {
			String storeId = patch.getBomid();  // StoreId "一定是人"

			if ((reviseId.equals(storeId)) || (reviseId.equals("Admin"))) {
				return mealBOMService.updateMealbom(reviseId, patch);
			} else {
				return new ResponseEntity<String>("路徑參數有誤：ReviseID 只能是 Admin or StoreId", HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	// 修改BOM資料 Patchmapping
//	@ApiOperation("修改BOM資料")
//	@PatchMapping(path="/boms/{reviseId}", consumes="application/json")
//	public MealBomBean patchBom(@PathVariable String reviseid, @RequestBody MealBomBean patch) {
//		// Fetch the data from the database
////		MealBomBean bom = mealBOMBeanRepository.findByBomid(reviseid).get(0);
//		
//		patch.setReviseid(reviseid);
//		
//		// 若要改資料庫欄位裡面的數值，可以從後端修改嗎？權限？！
////		if(patch.getIngredientid() != null) {
////			bom.setIngredientid(patch.getIngredientid());
////		}
////		if(patch.getIngredientname() != null) {
////			bom.setIngredientname(patch.getIngredientname());
////		}
//		return mealBOMBeanRepository.save(patch);
//	}
	
}
