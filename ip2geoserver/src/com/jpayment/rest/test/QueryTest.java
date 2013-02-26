/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpayment.rest.api.object.IPGeoCountryRequest;

public class QueryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL("http://localhost:8080/ip2geo/rest/ipinfo/country");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("content-type", "application/json");
			ObjectMapper mapper = new ObjectMapper();
			IPGeoCountryRequest ip = new IPGeoCountryRequest();
			ip.setIp("192.168.0.1");
			String json = mapper.writeValueAsString(ip);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setReadTimeout(10000);

			con.connect();
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());

			System.out.println(json);
			wr.write(URLEncoder.encode(json, "UTF-8"));
			wr.flush();
			wr.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
