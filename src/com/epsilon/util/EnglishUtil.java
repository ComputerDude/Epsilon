package com.epsilon.util;

public class EnglishUtil {

	public static String firstUpper(String s) {
		// What the hell is this super inefficient code
		// Also you should just use StringUtils#capitalize(String) --null
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
}
