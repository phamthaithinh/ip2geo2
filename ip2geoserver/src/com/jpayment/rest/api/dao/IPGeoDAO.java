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
import com.jpayment.rest.entity.IPGeoCountry;
import com.jpayment.rest.entity.IPGeoCountry_;

public class IPGeoDAO {
	public IPGeoCountry getCountrybyIP(Long ip) {
		EntityManager em = JPAHelper.getEntityManager();
		IPGeoCountry reEntity = null;
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<IPGeoCountry> c = qb.createQuery(IPGeoCountry.class);
		Root<IPGeoCountry> p = c.from(IPGeoCountry.class);
		Predicate greater = qb.ge(p.get(IPGeoCountry_.toIp), ip);
		Predicate lower = qb.le(p.get(IPGeoCountry_.fromIp), ip);
		c.where(qb.and(greater, lower));
		TypedQuery<IPGeoCountry> q = em.createQuery(c);
		List<IPGeoCountry> result = q.getResultList();
		if (result.size() > 0) {
			reEntity = result.get(0);
		}
		return reEntity;
	}
	public IPGeoCountry saveorUpdate(IPGeoCountry entity) {
		EntityManager manager = JPAHelper.getEntityManager();
		if (entity.getId() != null && entity.getId() > 0) {
			manager.merge(entity);
		} else {
			manager.persist(entity);
		}
		return entity;
	}
	public void truncateIPGeoTable(){
		EntityManager manager = JPAHelper.getEntityManager();
		Query query=manager.createNativeQuery("truncate table dixi_ip2country");
		query.executeUpdate();
	}
}
