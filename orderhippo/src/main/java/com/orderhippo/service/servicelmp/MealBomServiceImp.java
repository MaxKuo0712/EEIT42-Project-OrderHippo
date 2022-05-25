package com.orderhippo.service.servicelmp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.MealBomBean;
import com.orderhippo.repository.MealBomBeanRepository;
import com.orderhippo.service.service.MealBomService;

@Service
public class MealBomServiceImp implements MealBomService {
	
	@Autowired
	private MealBomBeanRepository mealBOMBeanRepository; 
	
	@Override
	public Object addBOM(MealBomBean mealBomBean) {
		
		if (mealBomBean != null) {
			try {
				MealBomBean result = mealBOMBeanRepository.save(mealBomBean);
				return new ResponseEntity<Boolean>(mealBOMBeanRepository.existsById(result.getId()),  HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
		
//		if(mealBOMBeanRepository.save(mealBomBean) != null) {
//			return true;
//		}
//		return false;
	}

	// 查詢所有 Meal BOM
	@Override
	public List<MealBomBean> getAllMealbom() {
		List<MealBomBean> result = mealBOMBeanRepository.findAll();
		if(result.size() > 0) {
			return result;
		}
		return null;
	}

	// 查詢單筆BOM By BOMId
	@Override
	public List<MealBomBean> getBomByBomid(String bomid) {
		List<MealBomBean> result = mealBOMBeanRepository.findByBomid(bomid);
		if(result.size() > 0) {
			return result;
		}
		return null;
	}

	// 查詢BOM By MealId
	@Override
	public List<MealBomBean> getBomByMealid(String mealid) {
		List<MealBomBean> result = mealBOMBeanRepository.findByMealid(mealid);
		if(result.size() > 0) {
			return result;
		}
		return null;
	}
	
	// 查詢BOM By ingredientid
	@Override
	public List<MealBomBean> getBomByIngredientid(String ingredientid) {
		List<MealBomBean> result = mealBOMBeanRepository.findByMealid(ingredientid);
		if(result.size() > 0) {
			return result;
		}
		return null;
	}

	// 查詢單筆BOM By MealName
//	@Override
//	public List<MealBomBean> getBomByMealname(String mealname) {
//		List<MealBomBean> result = mealBOMBeanRepository.findByMealname(mealname);
//		if(result.size() > 0) {
//			return result;
//		}
//		return null;
//	}

	// 查詢單筆BOM By MealName + IngredientName
//	@Override
//	public List<MealBomBean> getBomByMealnameAndIngredientname(String mealname, String ingredientname) {
//		List<MealBomBean> result = mealBOMBeanRepository.findByMealnameAndIngredientname(mealname, ingredientname);
//		if(result.size() > 0) {
//			return result;
//		}
//		return null;
//	}

	// 修改BOM資料
	@Override
	public Object updateMealbom(String reviseid, MealBomBean mealBomBean) {
		List<MealBomBean> result = mealBOMBeanRepository.findByBomid(mealBomBean.getBomid());
		
		if(result.size() == 1) {
			MealBomBean currentMealBom = result.get(0);
			
			mealBomBean.setId(currentMealBom.getId());
			mealBomBean.setBomid(currentMealBom.getBomid());
			mealBomBean.setRevisetime(new Date());
			mealBomBean.setReviseid(reviseid);
			
			try {
				mealBOMBeanRepository.save(mealBomBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);   // 重覆update一樣的資料，前端去處理，後端都吃進來
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：BomID", HttpStatus.NOT_FOUND);
	}
}
