package com.jpayment.rest.api.service.ipml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.io.FileUtils;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.jpayment.rest.JPAHelper;
import com.jpayment.rest.api.dao.IPGeoDAO;
import com.jpayment.rest.entity.IPGeoCountry;
import com.jpayment.rest.util.ConfigReader;

public class CSVServiceImpl {
	private IPGeoDAO dao= new IPGeoDAO();
	public static String DEFAULT_URL = "http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip";

	public void loaCSV(String dataurl) throws IOException {
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

	private void pushCSV2DB(File csvFile) throws FileNotFoundException {
		Reader reader = new FileReader(csvFile);

		CSVReader<String[]> csvPersonReader = CSVReaderBuilder
				.newDefaultReader(reader);
		Iterator<String[]> iter = csvPersonReader.iterator();
		EntityManager em = JPAHelper.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			dao.truncateIPGeoTable();
			while (iter.hasNext()) {
				String[] a=iter.next();
				String[] geoCountry=a[0].split(",");
				IPGeoCountry bean= new IPGeoCountry();
				bean.setFromSIp(geoCountry[0]);
				bean.setToSIp(geoCountry[1]);
				bean.setFromIp(Long.parseLong(geoCountry[2]));
				bean.setToIp(Long.parseLong(geoCountry[3]));
				bean.setCode2(geoCountry[4]);
				bean.setName(geoCountry[5]);
				dao.saveorUpdate(bean);
			}
			txn.commit();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			if (txn != null && txn.isActive()) {
				txn.rollback();
			}
		} finally {
			txn = null;
			em.close();
		}

	}
}
