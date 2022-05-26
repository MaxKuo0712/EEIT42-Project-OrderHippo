package com.orderhippo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProjectUtils {
	
	public static String createToken(String requestID) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder.encode(requestID);
	}
	
	public static boolean verifyToken(String realHashToken, String dbToken) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(realHashToken, dbToken);
	}
}
