package com.orderhippo.test;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.orderhippo.hibernate.HibernateUtil;
import com.orderhippo.model.UserInfoBean;

public class UserInfoTest {

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1993, 07, 12);
		Date date = calendar.getTime();
		
		UserInfoBean userInfo = new UserInfoBean();
//		userInfo.setUsername("Max Kuo");
//		userInfo.setUserGender("M");
//		userInfo.setUserBirth(date);	
//		userInfo.setUserPhone("0955820712");
//		userInfo.setUserAddress("台中市西屯區福中十一街43之1號");
//		userInfo.setUserMail("maxkuo712@gmail.com");
		session.save(userInfo);
		
		transaction.commit();
		session.close();
		factory.close();
	}

}
