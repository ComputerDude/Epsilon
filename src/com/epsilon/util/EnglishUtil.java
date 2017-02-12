package com.epsilon.util;

public class EnglishUtil {

	public static String firstUpper(String s) {
		
		s = s.toLowerCase();
		char c = s.charAt(0);
		s = s.substring(1, s.length());
		String u = "" + c;
		u = u.toUpperCase();
		s = u + s;
		
		return s;
	}
	
}
