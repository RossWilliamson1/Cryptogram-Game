package models;

import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author Paul Hutchison
 *
 */
public class LetterCryptogram extends Cryptogram {
		
	public LetterCryptogram () {
		super();
		if (getPhrase()!=null) {			
			this.cryptoAlpha = generateAlpha();
		}
	}
	
	public LetterCryptogram (String fileLocation) {
		super(fileLocation);
		if (getPhrase()!=null) {			
			this.cryptoAlpha = generateAlpha();
		}
	}
	
	public LetterCryptogram (String phrase, HashMap<Character, String> cryptoAlpha) {
		super(phrase,cryptoAlpha);
		this.cryptoAlpha = cryptoAlpha;
	}
	
	/**
	 * Generates the alphabet for which the game will be played with
	 * returns the newly randomly generated alphabet
	 * @return alpha
	 */	
	protected HashMap<Character, String> generateAlpha() {
		HashMap<Character, String> newAlpha = new HashMap<>();
		int baseIndex, alphaIndex=0;
		Random rnd = new Random();
		
		do {
			baseIndex = rnd.nextInt(LetterCryptogram.ALPHABET.length());
			if (alphaIndex!=baseIndex) {
				if (!newAlpha.containsKey(ALPHABET.charAt(alphaIndex)) && 
						!newAlpha.containsValue(ALPHABET.charAt(baseIndex)+"")) {
					newAlpha.put(ALPHABET.charAt(alphaIndex), ALPHABET.charAt(baseIndex)+"");
					alphaIndex++;
				}
			}
		} while (alphaIndex<LetterCryptogram.ALPHABET.length());
		
		return newAlpha;
	}
	
	/* ----- Method ------ */
	/**
	 * Takes a letter in the form of the alphabet and un encrypts it.
	 * @return regular character
	 */
	@Override
	public char getPlainLetter(char cryptoletter) {		
		for (int i=0; i<ALPHABET.length(); i++) {
			if (cryptoAlpha.get(ALPHABET.charAt(i)).equals(cryptoletter+"")) {
				return ALPHABET.charAt(i);
			}
		}
		return ' ';
	}
	
	@Override
	public String toString() {
		String toPrint="";
		for (int i=0; i<getPhrase().length(); i++) {
			if (getPhrase().charAt(i)==' ') {
				toPrint = toPrint.concat(" ");
			} else {
				toPrint = toPrint.concat(cryptoAlpha.get(getPhrase().charAt(i)));
			}
		}
		return toPrint;
	}
}
