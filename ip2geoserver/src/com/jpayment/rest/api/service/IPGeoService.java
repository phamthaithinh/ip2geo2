package com.jpayment.rest.api.service;


import com.jpayment.rest.entity.IPGeoCountry;

public interface IPGeoService {
	public IPGeoCountry getCountry(String ip);
	public void reload() throws Exception ;
	public void deletedb()throws Exception ;
}
