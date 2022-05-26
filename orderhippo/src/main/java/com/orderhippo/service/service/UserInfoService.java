package com.orderhippo.service.service;

import java.util.List;

import com.orderhippo.model.UserInfoBean;

public interface UserInfoService {
	// 新增單筆使用者資料
    public Object addUserInfo(UserInfoBean userInfoBean);
    
    // 取得全部使用者資料
    public List<UserInfoBean> getAllUserInfo();
    
    // 取得單筆使用者資料 by UserId
    public List<UserInfoBean> getUserInfofindByUserid(String userid);
    
    // 取得單筆使用者資料 by UserMail
    public List<UserInfoBean> getUserInfofindfindByUsermail(String usermail);
    
    // 更新單筆使用者資料
    public Object updateUserInfo(String requestID, UserInfoBean userInfoBean);
}
