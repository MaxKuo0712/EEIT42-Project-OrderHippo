package com.orderhippo.test;

import com.orderhippo.utils.ProjectUtils;

public class TokenTest {

	public static void main(String[] args) {
		
		String dbToken = "$2a$10$KFBKQ9Tve5if/gxp36.VqOVy4Cj8ep9W2m1oupIyNr6ZobqpIOOWO";
		String realHashToken = "$2a$10$yP9nyDYa500O3nD0/Bi.FOjEGw93bluuTPvL8N0PBKQm6FX3xPWnG";
		
		System.out.println(ProjectUtils.verifyToken(realHashToken, dbToken));
	}

}
