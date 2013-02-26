/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api.service.ipml;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.api.dao.IPGeoCityBlockDAO;
import com.jpayment.rest.api.dao.IPGeoCityLocationDAO;
import com.jpayment.rest.api.dao.IPGeoDAO;
import com.jpayment.rest.api.service.IPGeoService;
import com.jpayment.rest.entity.GeoCityBlock;
import com.jpayment.rest.entity.GeoCityLocation;
import com.jpayment.rest.entity.IPGeoCountry;
import com.jpayment.rest.util.ConfigReader;
import com.jpayment.rest.util.IPUtils;

public class IPGeoServiceImpl implements IPGeoService {
	private IPGeoDAO dao = new IPGeoDAO();
	private IPGeoCityBlockDAO geoCityBlockDAO = new IPGeoCityBlockDAO();
	private IPGeoCityLocationDAO geoCityLocationDAO = new IPGeoCityLocationDAO();

	public IPGeoCountry getCountry(String ip) throws Exception {
		EntityManager em = JPAHelper.getEntityManager();
		try {
			IPGeoCountry bean = dao.getCountrybyIP(IPUtils.ip2long(ip));
			return bean;
		} catch (Exception ex) {
			throw ex;
		} finally {
			em.close();
		}
	}

	public void load() throws Exception {
		CSVServiceImpl service = new CSVServiceImpl();
		try {
			service.loaCSV(ConfigReader
					.readConfig("maxmind.geodatabase.country.url"));
		} catch (Exception e) {
			throw e;
		}
	}

	public void deletedb() throws Exception {
		EntityManager em = JPAHelper.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		try {
			txn.begin();
			dao.truncateIPGeoTable();
			txn.commit();
		} catch (Exception ex) {
			if (txn != null && txn.isActive())
				txn.rollback();
			throw ex;
		} finally {
			em.close();
		}

	}

	public void reload() throws Exception {
		deletedb();
		load();
	}

	@Override
	public GeoCityLocation getCity(String ip) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = JPAHelper.getEntityManager();
		try {
			GeoCityBlock bean = geoCityBlockDAO.getCountrybyIP(IPUtils
					.ip2long(ip));
			GeoCityLocation geoLocation = null;
			if (bean != null) {
				geoLocation = geoCityLocationDAO.get(bean.getLocalId());
			}
			return geoLocation;
		} catch (Exception ex) {
			throw ex;
		} finally {
			em.close();
		}
	}
}
