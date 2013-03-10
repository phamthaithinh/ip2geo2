package com.jpayment.rest.test;

import com.jpayment.rest.api.service.ipml.CSVServiceImpl;

public class LoadGeoCityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSVServiceImpl service= new CSVServiceImpl();
		try {
			service.loaCSV("file:///C:/GeoIPCountryCSV.zip");
			service.loaCSVCity("file:///C:/GeoLiteCity-latest.zip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
