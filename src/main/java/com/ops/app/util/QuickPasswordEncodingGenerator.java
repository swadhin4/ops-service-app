package com.ops.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickPasswordEncodingGenerator {

	/**
	 * @param args
	 */
	public static String encodePassword(String rawPassword) {
		//String password = "swadhin4";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawPassword);
	}

	/*public static void main(String[] args){
		System.out.println(encodePassword("mkp006"));
	}
*/
}