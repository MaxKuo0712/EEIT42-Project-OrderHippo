package com.orderhippo.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
		// 讀取組態檔
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
		
		// 產生SessionFactory
		try {
			return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
	
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void closeSessionFactory() {
    	if (sessionFactory != null) {
    		sessionFactory.close();
    	}
    }
}
