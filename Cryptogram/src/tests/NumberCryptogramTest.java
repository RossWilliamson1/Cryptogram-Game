package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import models.NumberCryptogram;

public class NumberCryptogramTest {

	@Test
	public void testGetCryptoAlpha() {
		// Test to see if the cryptogram alphabet has enough letters
		assertFalse("1".equals(new NumberCryptogram().getCryptoAlpha().get('1')));
		assertFalse("2".equals(new NumberCryptogram().getCryptoAlpha().get('2')));
		assertFalse("3".equals(new NumberCryptogram().getCryptoAlpha().get('3')));
	}
}
