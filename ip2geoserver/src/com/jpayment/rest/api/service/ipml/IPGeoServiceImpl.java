/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api.service.ipml;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.api.dao.IPGeoDAO;
import com.jpayment.rest.api.service.IPGeoService;
import com.jpayment.rest.entity.IPGeoCountry;
import com.jpayment.rest.util.ConfigReader;
import com.jpayment.rest.util.IPUtils;

public class IPGeoServiceImpl implements IPGeoService {
	private IPGeoDAO dao = new IPGeoDAO();

	public IPGeoCountry getCountry(String ip) {
		return dao.getCountrybyIP(IPUtils.ip2long(ip));
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
		try{
		txn.begin();
		dao.truncateIPGeoTable();
		txn.commit();
		}catch(Exception ex){
			if(txn!=null&&txn.isActive())txn.rollback();
			throw ex;
		}
		finally{
			em.close();
		}
		
	}

	public void reload() throws Exception {
		deletedb();
		load();
	}
}
