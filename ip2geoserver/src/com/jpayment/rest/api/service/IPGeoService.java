/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api.service;


import com.jpayment.rest.entity.GeoCityLocation;
import com.jpayment.rest.entity.IPGeoCountry;

public interface IPGeoService {
	public IPGeoCountry getCountry(String ip)throws Exception ;
	public GeoCityLocation getCity(String ip)throws Exception ;
	public void reload() throws Exception ;
	public void deletedb()throws Exception ;
}
