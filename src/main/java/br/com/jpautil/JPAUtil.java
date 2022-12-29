package br.com.jpautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	
	private static EntityManagerFactory factory = null;
	
	static {
		
		if (factory == null) {
			factory =  Persistence.createEntityManagerFactory("jsf");
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
}
