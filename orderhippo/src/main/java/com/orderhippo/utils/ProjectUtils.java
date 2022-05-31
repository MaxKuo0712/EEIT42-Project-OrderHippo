package com.orderhippo.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.orderhippo.model.StoreInfoBean;
import com.orderhippo.model.UserInfoBean;

public class ProjectUtils {
	
	public static String createToken(String requestID) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom()); 
		return encoder.encode(requestID);
	}
	
	public static boolean verifyToken(String realHashToken, String dbToken) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(realHashToken, dbToken);
	}
	
	public static String getDBToken(List<UserInfoBean> userinfo, List<StoreInfoBean> storeinfo, String requestID) {
		if (userinfo != null) {
			if (userinfo.get(0).getUserid().equals(requestID)) {
				return userinfo.get(0).getUsertoken();
			}
		} else if (storeinfo != null) {
			if (storeinfo.get(0).getStoreid().equals(requestID)) {
				return storeinfo.get(0).getStoretoken();
			}
		}
		return null;
	}
}
