package com.jpayment.ip2geo;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IP2GeoClientHelper client= new IP2GeoClientHelper();
		System.out.print(client.getCountry("77.240.60.203").toString());

	}

}
