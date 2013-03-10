package com.jpayment.rest.api.object;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IPGeoCityRequest {
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static IPGeoCityRequest parser(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, IPGeoCityRequest.class);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unknown format");
		}
	}
}
