package com.orderhippo.test;

import com.orderhippo.utils.ProjectUtils;

public class TokenTest {

	public static void main(String[] args) {
		
		String dbToken = "$2a$10$9eCdZngrRHRoGdby5tkuKO2pDzN3Vy7KgnV4JzWPwzgRghcG59XMC";
		String realHashToken = "$2a$10$gxA9g0lueDipV.a8wv65Le3g.ws9529rNceRHJHrsvqqGvEOprSBC";
		
		System.out.println(ProjectUtils.verifyToken(realHashToken, dbToken));
	}

}
