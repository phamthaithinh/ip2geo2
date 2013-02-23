package com.jpayment.rest.util;

public class IPUtils {
	public static Long ip2long(String ip) {
		String[] arrStr = ip.split("\\.");
		if (arrStr.length != 4)
			throw new IllegalArgumentException("Wrong IP address");
		Long longIP = Long.parseLong(arrStr[0]) * 256 * 256 * 256
				+ Long.parseLong(arrStr[1]) * 256 * 256
				+ Long.parseLong(arrStr[2]) * 256 + Long.parseLong(arrStr[3]);
		return longIP;
	}
}
