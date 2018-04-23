package com.pernix.services;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.pernix.entities.Service;

public class ServiceService extends ServiceCrud<Service> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;
	Method method;

	public Service insertService(Service service) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			startEntityManagerFactory();
			em.getTransaction().begin();
			em.persist(service);
			em.getTransaction().commit();
			Query qry = em.createQuery("SELECT MAX(t.id) FROM Service t");
		    int serviceId = (int) qry.getSingleResult();
		    System.out.println(serviceId);
			stopEntityManagerFactory();
			service.setId(serviceId);
			return service;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static void startEntityManagerFactory() {

		if (entityManagerFactory == null) {

			try {

				entityManagerFactory = Persistence.createEntityManagerFactory("E-Invoicing-API");

				em = entityManagerFactory.createEntityManager();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	public static void stopEntityManagerFactory() {

		if (entityManagerFactory != null) {

			if (entityManagerFactory.isOpen()) {

				try {

					entityManagerFactory.close();

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

			em.close();

			entityManagerFactory = null;

		}
	}
}
