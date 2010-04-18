package com.pferrot.security.passwordgenerator;

import java.util.Random;

public class PasswordGenerator {
	
	private static Random RANDOM = new Random();
	
	private static final int MIN_SIZE = 5;
	private static final int MAX_SIZE = 8;
	private static char[] AVAILABLE_CHARACTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
												  'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
												  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	/**
	 * Return a random password containing 5 to 8 characters.
	 * Characters are a-z, A-Z, 0-9.
	 * 
	 * @return
	 */
	public static String getNewPassword() {		
		return getNewPassword(getRandom(MIN_SIZE, MAX_SIZE));	
	}

	/**
	 * Return a random password containing the specified number of characters.
	 * Characters are a-z, A-Z, 0-9.
	 * 
	 * @param passwordSize
	 * @return
	 */
	public static String getNewPassword(final int pPasswordSize) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < pPasswordSize; i++) {
			char nextCharacter = AVAILABLE_CHARACTERS[getRandom(0, AVAILABLE_CHARACTERS.length - 1)];
			result.append(nextCharacter);
		}		
		return result.toString();	
	}

	/**
	 * 
	 * @param pMinValue
	 * @param pMaxValue
	 * @return
	 */
	public static int getRandom(final int pMinValue, final int pMaxValue) {
		final int difference = pMaxValue - pMinValue;
		float tempFloat = RANDOM.nextFloat();
		tempFloat = tempFloat * difference; 
		return Math.round(tempFloat) + pMinValue;		
	}
}
