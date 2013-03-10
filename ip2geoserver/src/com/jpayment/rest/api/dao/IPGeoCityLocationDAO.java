package com.jpayment.rest.api.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.entity.GeoCityLocation;

public class IPGeoCityLocationDAO {

	public GeoCityLocation saveorUpdate(GeoCityLocation entity)
			throws Exception {
		EntityManager manager = JPAHelper.getEntityManager();
		if (entity.getId() != null && entity.getId() > 0) {
			manager.merge(entity);
		} else {
			manager.persist(entity);
		}
		return entity;
	}

	public void truncateTable() {
		EntityManager manager = JPAHelper.getEntityManager();
		Query query = manager
				.createNativeQuery("truncate table dixi_geocity_location");
		query.executeUpdate();
	}

	public GeoCityLocation get(Long id) {
		EntityManager em = JPAHelper.getEntityManager();
		GeoCityLocation geoLocation = em.find(GeoCityLocation.class, id);
		return geoLocation;
	}

}
