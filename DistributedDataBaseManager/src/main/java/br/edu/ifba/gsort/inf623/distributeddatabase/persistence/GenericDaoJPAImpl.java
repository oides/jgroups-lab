package br.edu.ifba.gsort.inf623.distributeddatabase.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class GenericDaoJPAImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected Class<T> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;

	public GenericDaoJPAImpl() {

		Class baseClass = getClass();

		while (!(baseClass.getGenericSuperclass() instanceof ParameterizedType)) {
			baseClass = baseClass.getSuperclass();
		}

		ParameterizedType genericSuperclass = (ParameterizedType) baseClass.getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public T create(T t) {
		this.entityManager.persist(t);
		return t;
	}

	@Override
	public T read(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public T update(T t) {
		return this.entityManager.merge(t);
	}

	@Override
	public void delete(T t) {
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
	}

	@Override
	public List<T> findAll() {
		Query query = this.entityManager.createQuery("select this from " + entityClass.getSimpleName() + " this", entityClass);
		return (List<T>) query.getResultList();
	}

	public List<T> executeFilter(Map<String, Object> params) {
		
		Query query = this.entityManager.createQuery(this.buildQuery(params), entityClass);
		this.addQueryParameters(query, params);
		
		return (List<T>) query.getResultList();
		
	}

	public List<T> executeQuery(String query, Map<String, Object> params) {
		
		Query q = this.entityManager.createQuery(query, entityClass);
		this.addQueryParameters(q, params);
		
		return (List<T>) q.getResultList();
		
	}

	public T executeQuerySingle(String query, Map<String, Object> params) {
		
		Query q = this.entityManager.createQuery(query, entityClass);
		this.addQueryParameters(q, params);
		
		return (T) q.getSingleResult();
		
	}

	private String buildQuery(Map<String, Object> params) {
		
		StringBuffer query = new StringBuffer();
		
		query.append("select this from " + entityClass.getSimpleName() + " this ");
		query.append("where ");
		
		boolean primeiroElemento = true;
		for (Entry<String, Object> param : params.entrySet()) {
			
			if (!primeiroElemento) {
				query.append("and ");
			} else {
				primeiroElemento= false;
			}
			
			query.append("this." + param.getKey() + " = :" + param.getKey() + " ");
			
		}
		
		return query.toString();
	}
	
	private void addQueryParameters(Query query, Map<String, Object> params) {
		
		for (Entry<String, Object> param : params.entrySet()) {
		
			if (param.getValue() instanceof Date) {

				query.setParameter(param.getKey(), this.getDateIgnoringTime(param.getValue()));

			} else {
				
				query.setParameter(param.getKey(), param.getValue());
				
			}
			
		}
		
	}

	private Object getDateIgnoringTime(Object value) {
		
	    try {
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dateWithoutTime = sdf.parse(sdf.format(value));
			
			return dateWithoutTime;
			
		} catch (ParseException e) {
			
			return null;
			
		}
	    
	}
	
}

