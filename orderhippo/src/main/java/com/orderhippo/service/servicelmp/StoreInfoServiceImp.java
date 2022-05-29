package com.orderhippo.service.servicelmp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.repository.StoreInfoRepository;
import com.orderhippo.service.service.StoreInfoService;

@Service
@Transactional(rollbackFor = SQLException.class)
public class StoreInfoServiceImp implements StoreInfoService {
	
	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Override
	public Object addStoreInfo(StoreInfoBean storeInfoBean) {
		if (storeInfoBean != null) {
			try {
//				StoreInfoBean result = storeInfoRepository.save(storeInfoBean);
//				return new ResponseEntity<Boolean>(storeInfoRepository.existsById(result.getId()), HttpStatus.OK);
				storeInfoRepository.save(storeInfoBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

	@Override
	public List<StoreInfoBean> getAllStoreInfo() {
		List<StoreInfoBean> result = storeInfoRepository.findAll();
		
		if (!result.isEmpty()) {
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
	
//	// 更新單筆店家資料
//	@Override
//	public boolean updateStoreInfo(String reviseid, String storeid, StoreInfoBean storeInfoBean) {
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
//			storeInfoRepository.save(storeInfoBean);
//			
//			return true;
//		}
//		return false;
//	}
	// 更新單筆店家資料
	@Override
	public Object updateStoreInfo(String requestID, StoreInfoBean storeInfoBean) {
		List<StoreInfoBean> storeInfo = storeInfoRepository.findByStoreid(storeInfoBean.getStoreid());
			
		if (storeInfo.size() == 1) {
			StoreInfoBean currentStoreInfo = storeInfo.get(0);

			storeInfoBean.setId(currentStoreInfo.getId());
			storeInfoBean.setStoreid(currentStoreInfo.getStoreid());
			storeInfoBean.setStoretoken(currentStoreInfo.getStoretoken());
			storeInfoBean.setRevisetime(new Date());
			storeInfoBean.setReviseid(requestID);
			
			try {
				storeInfoRepository.save(storeInfoBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：StoreID", HttpStatus.NOT_FOUND);
	}

	@Override
	public List<StoreInfoBean> getStoreInfoByStoreid(String storeid) {
		List<StoreInfoBean> result = storeInfoRepository.findByStoreid(storeid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
}
