package com.jpayment.rest.api.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.entity.GeoCityBlock;
import com.jpayment.rest.entity.GeoCityBlock_;

public class IPGeoCityBlockDAO {
	public GeoCityBlock saveOrUpdate(GeoCityBlock entity) {
		EntityManager manager = JPAHelper.getEntityManager();
		if (entity.getId() != null && entity.getId() > 0) {
			manager.merge(entity);
		} else {
			manager.persist(entity);
		}
		return entity;
	}
	public void truncateTable(){
		EntityManager manager = JPAHelper.getEntityManager();
		Query query=manager.createNativeQuery("truncate table dixi_geocity_block");
		query.executeUpdate();
	}
	public GeoCityBlock getCountrybyIP(Long ip) {
		EntityManager em = JPAHelper.getEntityManager();
		GeoCityBlock reEntity = null;
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<GeoCityBlock> c = qb.createQuery(GeoCityBlock.class);
		Root<GeoCityBlock> p = c.from(GeoCityBlock.class);
		Predicate greater = qb.ge(p.get(GeoCityBlock_.endIP), ip);
		Predicate lower = qb.le(p.get(GeoCityBlock_.startIP), ip);
		c.where(qb.and(greater, lower));
		TypedQuery<GeoCityBlock> q = em.createQuery(c);
		List<GeoCityBlock> result = q.getResultList();
		if (result.size() > 0) {
			reEntity = result.get(0);
		}
		return reEntity;
	}
	
}
