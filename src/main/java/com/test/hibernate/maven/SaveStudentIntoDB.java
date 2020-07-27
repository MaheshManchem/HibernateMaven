package com.test.hibernate.maven;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
public class SaveStudentIntoDB {
	
	static Student studentObj;
    static Session sessionObj;
    static SessionFactory sessionFactoryObj;
    
	private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.addAnnotatedClass(com.test.hibernate.maven.Student.class);
        configObj.configure("hibernate.cfg.xml");
 
        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().
        											applySettings(configObj.getProperties()).build(); 
 
        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }
	
	public static void main(String[] args) {
		
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();
			/*
			EntityManager entitymanager = entityManagerFactory.createEntityManager();
			*/
			Student ss = new Student(101,"mahesh","manchem",(long)949024250);
			
			sessionObj.save(ss);
			System.out.println("saved successfully");
			sessionObj.getTransaction().commit();
			
		}catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }	
        }
		
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();
			Student ss2 = (Student) sessionObj.get(Student.class,101);
			System.out.println(ss2);
			
		}catch(Exception e1) {
			System.out.println("entry not retrived");
		}
		finally {
			if(sessionObj != null) {
                sessionObj.close();
            }	
		}
	}
}