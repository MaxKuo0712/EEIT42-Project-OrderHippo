package com.orderhippo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderhippo.model.UserInfoBean;
import com.orderhippo.repository.UserInfoRepository;

@Service
public class UserInfoServiceImp implements UserInfoService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	// 新增單筆使用者資料
	@Override
	public boolean addUserInfo(UserInfoBean userInfoBean) {
		List<UserInfoBean> userInfo = userInfoRepository.findByUserid(userInfoBean.getUserid());
		
		if (userInfo.size() == 0) {
			userInfoRepository.save(userInfoBean);
			return true;
		}
		return false;
	}

	// 取得全部使用者資料
	@Override
	public List<UserInfoBean> getAllUserInfo() {
		List<UserInfoBean> result = userInfoRepository.findAll();
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}
	
	// 取得單筆使用者資料 by UserId
	@Override
	public List<UserInfoBean> getUserInfofindByUserid(String userid) {
		List<UserInfoBean> result = userInfoRepository.findByUserid(userid);
		
		if (result.size() > 0) {
			return result;
		}
		return null;
	}
	
	// 取得單筆使用者資料 by UserMail
	@Override
	public List<UserInfoBean> getUserInfofindfindByUsermail(String usermail) {
		List<UserInfoBean> result = userInfoRepository.findByUsermail(usermail);
		
		if (result.size() > 0) {
			return result;
		} 
		return null;
	}

	// 更新單筆使用者資料
//	@Override
//	public ResponseEntity<Object> updateUserInfo(String reviseId, String userid, UserInfoBean userInfoBean) {
//		List<UserInfoBean> userInfo = userInfoRepository.findByUserid(userid);
//		
//		if (userInfo.size() == 1) {
//			UserInfoBean currentUserInfo = userInfo.get(0);
//			
//			userInfoBean.setId(currentUserInfo.getId());
//			userInfoBean.setUserid(userid);
//			if (userInfoBean.getUsername() == null) userInfoBean.setUsername(currentUserInfo.getUsername());
//			if (userInfoBean.getUsergender() == null) userInfoBean.setUsergender(currentUserInfo.getUsergender());
//			if (userInfoBean.getUserphone() == null) userInfoBean.setUserphone(currentUserInfo.getUserphone());
//			if (userInfoBean.getUsermail() == null) userInfoBean.setUsermail(currentUserInfo.getUsermail());
//			if (userInfoBean.getUserbirth() == null) userInfoBean.setUserbirth(currentUserInfo.getUserbirth());
//			if (userInfoBean.getUserage() == null) userInfoBean.setUserage(currentUserInfo.getUserage());
//			if (userInfoBean.getUseraddress() == null) userInfoBean.setUseraddress(currentUserInfo.getUseraddress());
//			if (userInfoBean.getLastlogintime() == null) userInfoBean.setLastlogintime(currentUserInfo.getLastlogintime());
//			userInfoBean.setReviseid(reviseId);
//			userInfoBean.setRevisetime(new Date());
//
//			return new ResponseEntity<>(userInfoRepository.save(userInfoBean), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	// 更新單筆使用者資料
	@Override
	public boolean updateUserInfo(String reviseId, String userid, UserInfoBean userInfoBean) {
		List<UserInfoBean> userInfo = userInfoRepository.findByUserid(userid);
		
		if (userInfo.size() == 1) {
			UserInfoBean currentUserInfo = userInfo.get(0);
			
			userInfoBean.setId(currentUserInfo.getId());
			userInfoBean.setUserid(userid);
			if (userInfoBean.getUsername() == null) userInfoBean.setUsername(currentUserInfo.getUsername());
			if (userInfoBean.getUsergender() == null) userInfoBean.setUsergender(currentUserInfo.getUsergender());
			if (userInfoBean.getUserphone() == null) userInfoBean.setUserphone(currentUserInfo.getUserphone());
			if (userInfoBean.getUsermail() == null) userInfoBean.setUsermail(currentUserInfo.getUsermail());
			if (userInfoBean.getUserbirth() == null) userInfoBean.setUserbirth(currentUserInfo.getUserbirth());
			if (userInfoBean.getUserage() == null) userInfoBean.setUserage(currentUserInfo.getUserage());
			if (userInfoBean.getUseraddress() == null) userInfoBean.setUseraddress(currentUserInfo.getUseraddress());
			if (userInfoBean.getLastlogintime() == null) userInfoBean.setLastlogintime(currentUserInfo.getLastlogintime());
			userInfoBean.setReviseid(reviseId);
			userInfoBean.setRevisetime(new Date());

			userInfoRepository.save(userInfoBean);
			
			return true;
		} 
		return false;
	}
}