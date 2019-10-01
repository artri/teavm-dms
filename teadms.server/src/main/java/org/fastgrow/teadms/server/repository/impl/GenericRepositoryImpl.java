package org.fastgrow.teadms.server.repository.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.fastgrow.teadms.server.repository.GenericRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class GenericRepositoryImpl<T, ID extends Serializable> 
	extends SimpleJpaRepository<T, ID> implements GenericRepository<T, ID> {
	
    private EntityManager entityManager;

    public GenericRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

    public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
    @Transactional
    public ID getId(T entity) {
    	Assert.notNull(entity, "The entity must not be null!");
        return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }
    
    @Override
    @Transactional
    public T requireById(ID id) throws EmptyResultDataAccessException {
    	Assert.notNull(id, "The given id must not be null!");
    	return findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
				String.format("No %s entity with id %s exists!", getDomainClass().getSimpleName(), id), 1));
    }
    
    @Transactional
    public List<T> findByAttributeContainsText(String attributeName, String text) {   	
    	return findByAttributeContainsText(attributeName, text, null);
    }
    
    @Transactional
	public List<T> findByAttributeContainsText(String attributeName, String text, Pager pager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getDomainClass());
        Root<T> root = query.from(getDomainClass());
    	String textParam = ("%" + StringUtils.trimToEmpty(text) + "%").toLowerCase();
    	query.select(root).where(
    			builder.like(
    					builder.lower(root.<String> get(attributeName)), textParam));
        TypedQuery<T> q = entityManager.createQuery(query);
		if (null != pager) {
			q.setFirstResult(pager.getOffset());
			q.setMaxResults(pager.getLimit());
		}
        return q.getResultList();		
	}
    
    @Transactional
    public List<T> findByAttributeEqualsText(String attributeName, String text) {
    	return findByAttributeEqualsText(attributeName, text, null);
    }

    @Transactional
    public List<T> findByAttributeEqualsText(String attributeName, String text, Pager pager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getDomainClass());
        Root<T> root = query.from(getDomainClass());
    	String textParam = StringUtils.trimToEmpty(text);
    	query.select(root).where(builder.equal(root.<String> get(attributeName), textParam));
        TypedQuery<T> q = entityManager.createQuery(query);
		if (null != pager) {
			q.setFirstResult(pager.getOffset());
			q.setMaxResults(pager.getLimit());
		}
        return q.getResultList();
    }
}
