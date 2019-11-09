package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import models.LetterCryptogram;

public class LetterCryptogramTest {

	@Test
	public void testGetCryptoAlpha() {
		// Test to see if the cryptogram alphabet has enough letters
		assertTrue(new LetterCryptogram().getCryptoAlpha().size() == 26);
		
		// Test to check that alphabet is different
		assertFalse("a".equals(new LetterCryptogram().getCryptoAlpha().get('a')));
		assertFalse("b".equals(new LetterCryptogram().getCryptoAlpha().get('b')));
		assertFalse("c".equals(new LetterCryptogram().getCryptoAlpha().get('c')));
	}

}
