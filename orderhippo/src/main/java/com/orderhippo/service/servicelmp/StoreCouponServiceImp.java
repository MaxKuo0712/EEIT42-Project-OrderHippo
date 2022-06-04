package com.orderhippo.service.servicelmp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.StoreCouponBean;
import com.orderhippo.repository.StoreCouponRepository;
import com.orderhippo.service.service.StoreCouponService;

@Service
public class StoreCouponServiceImp implements StoreCouponService {
	@Autowired
	StoreCouponRepository storeCouponRepository;
	
	// 查詢所有優惠卷
	@Override
	public List<StoreCouponBean> getAllCoupons() {
		List<StoreCouponBean> result = storeCouponRepository.findAll();
		
		if (!result.isEmpty()) {
			return result;
		} 
		return null;
	}

	// 新增單筆優惠卷
	@Override
	public Object addStoreCoupon(StoreCouponBean storeCouponBean) {
		if (storeCouponBean != null) {
			try {
//				StoreCouponBean result = storeCouponRepository.save(storeCouponBean);
//				return new ResponseEntity<StoreCouponBean>(result, HttpStatus.OK);
				storeCouponRepository.save(storeCouponBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("Input不存在", HttpStatus.NOT_FOUND);
	}

	// 查詢單筆優惠卷 By CouponID
	@Override
	public List<StoreCouponBean> getStoreCouponByCouponId(String couponid) {
		List<StoreCouponBean> result = storeCouponRepository.findByCouponid(couponid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 查詢單筆優惠卷 By StoreID
	@Override
	public List<StoreCouponBean> getStoreCouponByStoreId(String storeid) {
		List<StoreCouponBean> result = storeCouponRepository.findByStoreid(storeid);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}

	// 修改優惠卷資料
	@Override
	public Object updateStoreCoupon(String requestID, StoreCouponBean storeCouponBean) {
		List<StoreCouponBean> coupon = storeCouponRepository.findByCouponid(storeCouponBean.getCouponid());
		
		if (coupon.size() == 1) {
			StoreCouponBean currentCoupons = coupon.get(0);
			
			storeCouponBean.setId(currentCoupons.getId());
			storeCouponBean.setCouponid(currentCoupons.getCouponid());
			storeCouponBean.setStoreid(currentCoupons.getStoreid());
			storeCouponBean.setReviseid(requestID);
			storeCouponBean.setRevisetime(new Date());
			
			try {
				storeCouponRepository.save(storeCouponBean);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：CouponID", HttpStatus.NOT_FOUND);
	}
	
	// 刪除單筆優惠卷
	@Override
	public Object deleteCoupon(String requestID, String couponId) {
		List<StoreCouponBean> coupon = storeCouponRepository.findByCouponid(couponId);
		
		if(coupon.size() == 1) {
			try {
				storeCouponRepository.deleteById(coupon.get(0).getId());
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<String>("資料不存在：CouponID", HttpStatus.NOT_FOUND);
	}

	@Override
	public Object getStoreCouponByCouponName(String couponname) {
		List<StoreCouponBean> result = storeCouponRepository.findByCouponname(couponname);
		
		if (!result.isEmpty()) {
			return result;
		}
		return null;
	}
}
