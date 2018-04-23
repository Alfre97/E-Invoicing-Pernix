package com.pernix.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ServiceCrud<E> {

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;
	Method method;

	public void insert(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
			startEntityManagerFactory();
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			//stopEntityManagerFactory();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Class<E> getEntityClass() {

		Class c = this.getClass();
		ParameterizedType parameterizedType = (ParameterizedType) c.getGenericSuperclass();
		Type res = parameterizedType.getActualTypeArguments()[0];
		return (Class<E>) res;
	}

	public E read(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			startEntityManagerFactory();
			Class cls = obj.getClass();
			method = cls.getMethod("getId");
			Object idObj = method.invoke(obj);
			Integer idObtenido = (Integer) idObj;
			E objRead = (E) em.find(obj.getClass(), idObtenido);
			//stopEntityManagerFactory();
			if (objRead != null) {
				return objRead;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
			return null;
		}
	}

	public E modify(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			if (read(obj) != null) {
				startEntityManagerFactory();
				em.getTransaction().begin();
				em.merge(obj);
				em.getTransaction().commit();
				//stopEntityManagerFactory();
				return obj;
			} else
				return null;
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
			return null;
		}
	}

	public boolean delete(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			if (read(obj) != null) {
				E objRead = read(obj);
				startEntityManagerFactory();
				em.getTransaction().begin();
				em.remove(em.contains(objRead) ? objRead : em.merge(objRead));
				em.getTransaction().commit();
				stopEntityManagerFactory();
				return true;
			} else
				stopEntityManagerFactory();
			return false;
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
			return false;
		}
	}

	public List<E> list(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			startEntityManagerFactory();
			if (obj != null) {
				String jpql = "SELECT t FROM " + obj.getClass().getSimpleName() + " t";
				List<E> lista = (List<E>) em.createQuery(jpql, obj.getClass()).getResultList();
				//stopEntityManagerFactory();
				if (lista != null) {
					return lista;
				} else
					return null;
			} else
				stopEntityManagerFactory();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<E> getEmitters(E obj) {
		try {
			startEntityManagerFactory();
			if (obj != null) {
				String jpql = "SELECT e FROM " + obj.getClass().getSimpleName() + " e where usertype='Emitter'";
				List<E> emitters = (List<E>) em.createQuery(jpql, obj.getClass()).getResultList();
				stopEntityManagerFactory();
				if (emitters != null) {
					return emitters;
				} else
					return null;
			} else
				stopEntityManagerFactory();
			return null;
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
			return null;
		}
	}

	public List<E> getReceivers(E obj) {
		try {
			startEntityManagerFactory();
			if (obj != null) {
				String jpql = "SELECT e FROM " + obj.getClass().getSimpleName() + " e where usertype='Receiver'";
				List<E> emitters = (List<E>) em.createQuery(jpql, obj.getClass()).getResultList();
				stopEntityManagerFactory();
				if (emitters != null) {
					return emitters;
				} else
					return null;
			} else
				stopEntityManagerFactory();
			return null;
		} catch (Exception e) {
			stopEntityManagerFactory();
			System.out.println(e.getCause().getMessage());
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
	
	public void getCredentialsPersistence() 
	{
		EntityManagerFactory managerFactory = null;
		Map<String, String> persistenceMap = new HashMap<String, String>();

		persistenceMap.put("javax.persistence.jdbc.url", System.getenv("JDBC_DATABASE_URL"));
		persistenceMap.put("javax.persistence.jdbc.user",System.getenv("JDBC_DATABASE_USERNAME"));
		persistenceMap.put("javax.persistence.jdbc.password", System.getenv("JDBC_DATABASE_PASSWORD"));

		managerFactory = Persistence.createEntityManagerFactory("E-Invoicing-API", persistenceMap);
		EntityManager manager = managerFactory.createEntityManager();
	}
	
	public static Connection getConnection() throws URISyntaxException, SQLException {
		System.out.println("entra");
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));
	    System.out.println(dbUri);
	    String username = dbUri.getUserInfo().split(":")[0];
	    System.out.println(username);
	    String password = dbUri.getUserInfo().split(":")[1];
	    System.out.println(password);
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

	    return DriverManager.getConnection(dbUrl, username, password);
	}
}
