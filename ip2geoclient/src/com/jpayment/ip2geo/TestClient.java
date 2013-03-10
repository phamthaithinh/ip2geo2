/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.ip2geo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.FileUtils;

public class TestClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		IP2GeoClientHelper client= new IP2GeoClientHelper();
//		System.out.print(client.getCity("77.240.60.203").toString());
//		System.out.print(client.getCountry("77.240.60.203").toString());
//		URL url;
//		try {
//			url = new URL("https://196.216.251.36/customerportal/WebService/TransactionStatusCheck.svc?wsdl");
//			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//				public boolean verify(String hostname, SSLSession session) {
//					if (hostname.equals("196.216.251.36"))
//						return true;
//					return false;
//				}
//			});
//			HttpsURLConnection connect= (HttpsURLConnection) url.openConnection();
//			connect.setDoOutput(true);
//			connect.setDoInput(true);
//			connect.connect();
//			FileUtils.copyInputStreamToFile(connect.getInputStream(), new File("C:/Users/vietnga/test.wsdl"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.print("000008888888888".substring(5));
		

	}

}
