package com.replad.utils;

import java.util.Random;

public class RandomString {
	private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private int RANDOM_STRING_LENGTH = 1;

	/**
	 * Constructor with String length.
	 * 
	 * @param charLength Specified length for the random string
	 */
	public RandomString(int charLength){
		this.RANDOM_STRING_LENGTH = charLength;
	}

	/**
	 * Generate a specified length random number String.
	 * 
	 * @return
	 */
	public String generateRandomString(){
		Random randomGenerator = new Random();
		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<RANDOM_STRING_LENGTH; i++){
			int number = randomGenerator.nextInt(CHAR_LIST.length());
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}
}
