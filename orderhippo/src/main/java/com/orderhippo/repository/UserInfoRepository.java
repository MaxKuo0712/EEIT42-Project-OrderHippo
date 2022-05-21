package com.orderhippo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderhippo.model.UserInfoBean;

public interface UserInfoRepository extends JpaRepository<UserInfoBean, Integer> {
	// by UserID查詢
	List<UserInfoBean> findByUserid(String userid);
	
	// by UserMail查詢
	List<UserInfoBean> findByUsermail(String usermail);
}
