package com.jpayment.rest.api.service.ipml;

import java.io.IOException;

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

	public void load() throws IOException {
		CSVServiceImpl service = new CSVServiceImpl();
		try {
			service.loaCSV(ConfigReader
					.readConfig("maxmind.geodatabase.country.url"));
		} catch (IOException e) {
			throw e;
		}
	}

	public void deletedb() {
		EntityManager em = JPAHelper.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		dao.truncateIPGeoTable();
		txn.commit();
	}

	public void reload() throws IOException {
		deletedb();
		load();
	}
}
