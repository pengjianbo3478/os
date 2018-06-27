package com.gyf.os.util;

public enum DateFormatStyle {
	
	/**
	 * yyyy-MM-dd
	 */
	SHORT("SHORT","yyyy-MM-dd"),
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	FULL("FULL","yyyy-MM-dd HH:mm:ss");
	
	private String key;

	private String value;

	private DateFormatStyle(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
