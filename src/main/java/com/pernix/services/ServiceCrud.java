package com.pernix.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ServiceCrud<E> {

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;
	Method method;

	public E insert(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			startEntityManagerFactory();
			em.getTransaction().begin();
			em.persist(obj);
			em.flush();
			em.getTransaction().commit();
			stopEntityManagerFactory();
			return obj;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
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
			stopEntityManagerFactory();
			if (objRead != null) {
				return objRead;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public E modify(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			if (read(obj) != null) {
				startEntityManagerFactory();
				em.getTransaction().begin();
				em.merge(obj);
				em.flush();
				em.getTransaction().commit();
				stopEntityManagerFactory();
				return obj;
			} else
				return null;
		} catch (Exception e) {
			System.out.println(e);
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
			System.out.println(e);
			return false;
		}
	}

	public List<E> list(E obj) throws Exception, IllegalArgumentException, InvocationTargetException {
		try {
			startEntityManagerFactory();
			if (obj != null) {
				String jpql = "SELECT t FROM " + obj.getClass().getSimpleName() + " t";
				List<E> lista = (List<E>) em.createQuery(jpql, obj.getClass()).getResultList();
				stopEntityManagerFactory();
				if (lista != null) {
					return lista;
				} else
					return null;
			} else
				stopEntityManagerFactory();
			return null;
		} catch (Exception e) {
			System.out.println(e);
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
			System.out.println(e);
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
			System.out.println(e);
			return null;
		}
	}

	public static void startEntityManagerFactory() {
		if (entityManagerFactory == null & em == null) {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory("e-invoicing");
				em = entityManagerFactory.createEntityManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void stopEntityManagerFactory() {
		if (entityManagerFactory != null & em != null) {
			if (entityManagerFactory.isOpen() & em.isOpen()) {
				try {
					em.close();
					entityManagerFactory.close();
					em = null;
					entityManagerFactory = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
