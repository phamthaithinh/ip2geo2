package com.jpayment.rest.api.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jpayment.rest.AbstractResponse;
import com.jpayment.rest.ResponseObject;

public class IPGeoCityResponse extends AbstractResponse implements
		ResponseObject {
	private String countryCode2;
	private String city;
	private String region;
	private String postalCode;
	private String latitude;
	private String longitude;
	private String metroCode;
	private String areaCode;
	private String countryName;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getMetroCode() {
		return metroCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getRegion() {
		return region;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Cannot parser object to json");
		}
	}
}
