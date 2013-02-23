package com.jpayment.rest.api.service;

import java.io.IOException;

import com.jpayment.rest.entity.IPGeoCountry;

public interface IPGeoService {
	public IPGeoCountry getCountry(String ip);
	public void reload() throws IOException ;
	public void deletedb();
}
