/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api.object;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IPGeoCountryRequest {
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static IPGeoCountryRequest parser(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, IPGeoCountryRequest.class);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unknown format");
		}
	}

}
