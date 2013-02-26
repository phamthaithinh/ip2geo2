/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.ip2geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.dixipay.cyclos.ext.util.ConfigReader;
import com.google.gson.Gson;

public class IP2GeoClientHelper {
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getCountry(String ip) {
		String getData = "{\"ip\":\"" + ip + "\"}";
		URL url;
		String msg = "";
		try {
			url = new URL(ConfigReader.getParam("rest.service.country.url"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoInput(true);
			con.setReadTimeout(10000);
			con.setRequestMethod("POST");

			con.connect();
			con.getOutputStream().write(getData.getBytes("UTF-8"));
			InputStream inp = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inp));

			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			br.close();
			inp.close();
			con.disconnect();
			Gson gson = new Gson();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = (HashMap<String, Object>) gson.fromJson(sb.toString(),
					map.getClass());
			map.put("code2", ((String)map.get("code2")).toLowerCase());
			return map;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		HashMap<String, Object> out = new HashMap<String, Object>();
		out.put("success", "false");
		out.put("msg", msg);
		return out;
	}
}
