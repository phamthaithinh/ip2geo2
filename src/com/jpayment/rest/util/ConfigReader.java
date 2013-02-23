package com.jpayment.rest.util;

import java.util.ResourceBundle;

public class ConfigReader {
	private static ResourceBundle resource = ResourceBundle.getBundle("ip2geo");

	public static String readConfig(String key) {
		return resource.getString(key);
	}

	public static synchronized void reloadConfig() {
		resource = ResourceBundle.getBundle("ip2geo");
	}
}
