package com.orderhippo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.repository.StoreInfoRepository;

@Service
public class StoreInfoServiceImp implements StoreInfoService {
	
	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Override
	public boolean addStoreInfo(StoreInfoBean storeInfoBean) {
		if (storeInfoRepository.save(storeInfoBean) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<StoreInfoBean> getAllStoreInfo() {
		List<StoreInfoBean> result = storeInfoRepository.findAll();
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}

//	@Override
//	public ResponseEntity<Object> updateStoreInfo(String reviseid, String storeid, StoreInfoBean storeInfoBean) {
//		List<StoreInfoBean> storeInfo = storeInfoRepository.findByStoreid(storeid);
//			
//		if (storeInfo.size() == 1) {
//			StoreInfoBean currentStoreInfo = storeInfo.get(0);
//
//			storeInfoBean.setId(currentStoreInfo.getId());
//			storeInfoBean.setStoreid(storeid);
//			if(storeInfoBean.getStorename() == null) storeInfoBean.setStorename(currentStoreInfo.getStorename());
//			if(storeInfoBean.getStoreaddress() == null) storeInfoBean.setStoreaddress(currentStoreInfo.getStoreaddress());
//			if(storeInfoBean.getStorephone() == null) storeInfoBean.setStorephone(currentStoreInfo.getStorephone());
//			if(storeInfoBean.getStoremail() == null) storeInfoBean.setStoremail(currentStoreInfo.getStoremail());
//			if(storeInfoBean.getStorelocation() == null) storeInfoBean.setStorelocation(currentStoreInfo.getStorelocation());
//			if(storeInfoBean.getStoreopenstatus() == null) storeInfoBean.setStoreopenstatus(currentStoreInfo.getStoreopenstatus());
//			storeInfoBean.setRevisetime(new Date());
//			storeInfoBean.setReviseid(reviseid);
//			
//			return new ResponseEntity<>(storeInfoRepository.save(storeInfoBean), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}		
//	}
	
	// 更新單筆店家資料
	@Override
	public boolean updateStoreInfo(String reviseid, String storeid, StoreInfoBean storeInfoBean) {
		List<StoreInfoBean> storeInfo = storeInfoRepository.findByStoreid(storeid);
			
		if (storeInfo.size() == 1) {
			StoreInfoBean currentStoreInfo = storeInfo.get(0);

			storeInfoBean.setId(currentStoreInfo.getId());
			storeInfoBean.setStoreid(storeid);
			if(storeInfoBean.getStorename() == null) storeInfoBean.setStorename(currentStoreInfo.getStorename());
			if(storeInfoBean.getStoreaddress() == null) storeInfoBean.setStoreaddress(currentStoreInfo.getStoreaddress());
			if(storeInfoBean.getStorephone() == null) storeInfoBean.setStorephone(currentStoreInfo.getStorephone());
			if(storeInfoBean.getStoremail() == null) storeInfoBean.setStoremail(currentStoreInfo.getStoremail());
			if(storeInfoBean.getStorelocation() == null) storeInfoBean.setStorelocation(currentStoreInfo.getStorelocation());
			if(storeInfoBean.getStoreopenstatus() == null) storeInfoBean.setStoreopenstatus(currentStoreInfo.getStoreopenstatus());
			storeInfoBean.setRevisetime(new Date());
			storeInfoBean.setReviseid(reviseid);
			
			storeInfoRepository.save(storeInfoBean);
			
			return true;
		}
		return false;
	}
}
