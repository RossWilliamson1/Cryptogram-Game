package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import models.LetterCryptogram;

public class CryptogramTest {

	@Test
	public void testGeneratePhrase() {
		// Testing to see if we can get a phrase from the default location
		assertNotNull(new LetterCryptogram().getPhrase());
		
		// Test to make sure that the function returns null when the file
		//		path is invalid
		assertNull(new LetterCryptogram("").getPhrase());
		
		// Test to make sure that the generate phrase function can get a
		//		phrase from a custom file. This file has one line in it rather
		// 		than fifteen
		assertEquals(new LetterCryptogram("src/tests/testPhraseFile.txt").getPhrase(), 
				"a file with some words in it");
		
	}
	
	@Test
	public void testGetFrequencies() {
		// Test to make sure that the list of frequencies is generated
		assertNotNull(new LetterCryptogram().getFrequencies());
		
		// Test a common letter 't' to see if it comes back with a valid 
		//		frequency
		assertTrue(new LetterCryptogram().getFrequencies().get('t')>0);
		
		// Test to see if it works when a random character is entered
		assertNull(new LetterCryptogram().getFrequencies().get('>'));
	}
}
