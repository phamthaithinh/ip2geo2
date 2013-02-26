/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api.service.ipml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.io.FileUtils;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.api.dao.IPGeoCityBlockDAO;
import com.jpayment.rest.api.dao.IPGeoCityLocationDAO;
import com.jpayment.rest.api.dao.IPGeoDAO;
import com.jpayment.rest.entity.GeoCityBlock;
import com.jpayment.rest.entity.GeoCityLocation;
import com.jpayment.rest.entity.IPGeoCountry;
import com.jpayment.rest.util.ConfigReader;

public class CSVServiceImpl {
	private IPGeoDAO dao = new IPGeoDAO();
	private final Long batch = Long.parseLong(ConfigReader
			.readConfig("batch.commit"));
	private IPGeoCityBlockDAO geoCityBlockDAO = new IPGeoCityBlockDAO();
	private IPGeoCityLocationDAO geoCityLocationDAO = new IPGeoCityLocationDAO();
	public static String DEFAULT_URL = "http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip";

	public static String DEFAULT_CITY_URL = "http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip";

	public void loaCSV(String dataurl) throws Exception {
		URL url = new URL(dataurl == null ? DEFAULT_URL : dataurl);
		String directory = ConfigReader.readConfig("folder.store.downoad");
		if (directory.endsWith("/"))
			directory = directory.substring(0, directory.length() - 2);
		clean();
		File zipfile = new File(directory + "/test.zip");
		FileUtils.copyURLToFile(url, zipfile);
		File cvsFile = unzip(zipfile);
		pushCSV2DB(cvsFile);
	}

	private void clean() {
		String directory = ConfigReader.readConfig("folder.store.downoad");
		File folder = new File(directory);
		if (folder.isDirectory()) {
			for (String filename : folder.list()) {
				File child = new File(folder, filename);
				child.delete();
			}
		}
	}

	public void loaCSVCity(String dataurl) throws Exception {
		URL url = new URL(dataurl == null ? DEFAULT_URL : dataurl);
		String directory = ConfigReader.readConfig("folder.store.downoad");
		if (directory.endsWith("/"))
			directory = directory.substring(0, directory.length() - 2);
		clean();
		File zipfile = new File(directory + "/geoCity.zip");
		FileUtils.copyURLToFile(url, zipfile);
		HashMap<String, File> cvsFile = unzipCity(zipfile);
		//pushGeoCity(cvsFile.get("block"), 0);
		pushGeoCity(cvsFile.get("location"), 1);
	}

	private File unzip(File zipfile) throws IOException {
		byte[] buffer = new byte[1024];

		try {
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipfile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			File newFile = null;
			while (ze != null) {

				String fileName = ze.getName();
				newFile = new File(
						ConfigReader.readConfig("folder.store.downoad")
								+ File.separator + fileName);

				System.out.println("file unzip : " + newFile.getAbsoluteFile());
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				break;
			}

			zis.closeEntry();
			zis.close();
			return newFile;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private HashMap<String, File> unzipCity(File zipfile) throws IOException {
		byte[] buffer = new byte[1024];
		HashMap<String, File> map = new HashMap<String, File>();
		try {
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipfile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				File newFile = null;
				String fileName = ze.getName();
				newFile = new File(
						ConfigReader.readConfig("folder.store.downoad")
								+ File.separator + fileName);

				System.out.println("file unzip : " + newFile.getAbsoluteFile());
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				if (ze.getName().contains("Blocks")) {
					map.put("block", newFile);
				} else {
					map.put("location", newFile);
				}
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
			return map;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private void pushCSV2DB(File csvFile) throws Exception {
		Reader reader = new FileReader(csvFile);

		CSVReader<String[]> csvPersonReader = CSVReaderBuilder
				.newDefaultReader(reader);
		Iterator<String[]> iter = csvPersonReader.iterator();
		EntityManager em = JPAHelper.getEntityManager();
		EntityTransaction txn = null;
		Long count = 0L;
		try {
			txn = em.getTransaction();
			txn.begin();
			while (iter.hasNext()) {
				count++;
				String[] a = iter.next();
				String[] geoCountry = a[0].split(",");
				IPGeoCountry bean = new IPGeoCountry();
				bean.setFromSIp(geoCountry[0]);
				bean.setToSIp(geoCountry[1]);
				bean.setFromIp(Long.parseLong(geoCountry[2]));
				bean.setToIp(Long.parseLong(geoCountry[3]));
				bean.setCode2(geoCountry[4]);
				bean.setName(geoCountry[5]);
				dao.saveorUpdate(bean);
				if (count % batch==0) {
					em.flush();
					em.clear();
					count = 0L;
				}
			}
			txn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (txn != null && txn.isActive()) {
				txn.rollback();
			}
			throw ex;
		} finally {
			txn = null;
			em.close();
		}

	}

	private void pushGeoCity(File csvFile, int type) throws Exception {
		Reader reader = new FileReader(csvFile);

		CSVReader<String[]> csvPersonReader = CSVReaderBuilder
				.newDefaultReader(reader);
		Iterator<String[]> iter = csvPersonReader.iterator();
		EntityManager em = JPAHelper.getEntityManager();
		EntityTransaction txn = null;
		Long count = 0L;
		try {
			if (type == 0) {
				// ignore first two rows
				iter.next();
				iter.next();
				txn = em.getTransaction();
				txn.begin();
				while (iter.hasNext()) {
					count++;
					String[] a = iter.next();
					String[] geoCityBlock = a[0].split(",");
					GeoCityBlock bean = new GeoCityBlock();
					bean.setStartIP(Long.parseLong(geoCityBlock[0]));
					bean.setEndIP(Long.parseLong(geoCityBlock[1]));
					bean.setLocalId(Long.parseLong(geoCityBlock[2]));
					geoCityBlockDAO.saveOrUpdate(bean);
					if (count % batch==0) {
						em.flush();
						em.clear();
						count = 0L;
					}
				}
				txn.commit();
			} else {
				// ignore first two rows
				iter.next();
				iter.next();
				txn = em.getTransaction();
				txn.begin();
				while (iter.hasNext()) {
					count++;
					String[] a = iter.next();
					String[] geoCityLocation = a[0].split(",");
					int length=geoCityLocation.length;
					GeoCityLocation bean = new GeoCityLocation();
					bean.setId(Long.parseLong(geoCityLocation[0]));
					bean.setCountryCode2(geoCityLocation[1]);
					bean.setRegion(length>2?geoCityLocation[2]:null);
					bean.setCity(length>3?geoCityLocation[3]:null);
					bean.setPostalCode(length>4?geoCityLocation[4]:null);
					bean.setLatitude(length>5?geoCityLocation[5]:null);
					bean.setLongitude(length>6?geoCityLocation[6]:null);
					bean.setMetroCode(length>7?geoCityLocation[7]:null);
					bean.setAreaCode(length>8?geoCityLocation[8]:null);
					geoCityLocationDAO.saveorUpdate(bean);
					if (count % batch==0) {
						em.flush();
						em.clear();
						count = 0L;
					}
				}
				txn.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (txn != null && txn.isActive()) {
				txn.rollback();
			}
			throw ex;
		} finally {
			txn = null;
			em.close();
		}
	}
}
