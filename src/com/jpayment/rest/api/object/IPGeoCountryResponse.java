package com.jpayment.rest.api.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpayment.rest.AbstractResponse;
import com.jpayment.rest.ResponseObject;

public class IPGeoCountryResponse extends AbstractResponse implements ResponseObject {
	private String code2;
	private String name;

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Cannot parser object to json");
		}
	}
}
