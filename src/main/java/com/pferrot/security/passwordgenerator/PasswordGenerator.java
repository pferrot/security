package com.pferrot.security.passwordgenerator;

import java.util.Random;

public class PasswordGenerator {
	
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
		Random random = new Random();
		
		// Password size.
		float tempFloat = random.nextFloat();
		tempFloat = tempFloat * (MAX_SIZE - MIN_SIZE);
		int passwordSize = MIN_SIZE + Math.round(tempFloat);
		
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < passwordSize; i++) {
			tempFloat = random.nextFloat();
			tempFloat = tempFloat * AVAILABLE_CHARACTERS.length - 1;
			char nextCharacter = AVAILABLE_CHARACTERS[Math.round(tempFloat)];
			result.append(nextCharacter);
		}
		
		return result.toString();	
	}
}
