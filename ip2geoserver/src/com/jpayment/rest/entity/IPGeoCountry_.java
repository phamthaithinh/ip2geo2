/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.entity;

import javax.persistence.metamodel.SingularAttribute;

public abstract class IPGeoCountry_ {
	public static volatile SingularAttribute<IPGeoCountry, Long> id;
	public static volatile SingularAttribute<IPGeoCountry, Long> fromIp;
	public static volatile SingularAttribute<IPGeoCountry, Long> toIp;
	public static volatile SingularAttribute<IPGeoCountry, String> fromSIp;
	public static volatile SingularAttribute<IPGeoCountry, String> toSIp;
	public static volatile SingularAttribute<IPGeoCountry, String> code2;
	public static volatile SingularAttribute<IPGeoCountry, String> name;
}
