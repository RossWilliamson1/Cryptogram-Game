package models;

import java.util.HashMap;
import java.util.Random;

/**
 * Model for creating and using a cryptogram that is represented in numbers
 * instead of letters.
 * @author Paul Hutchison
 */
public class NumberCryptogram extends Cryptogram  {
	/**
	 * Base constructor that generates the cryptogram using a phrase
	 * from the basic file
	 */
	public NumberCryptogram () {
		super();
		if (getPhrase()!=null) {
			this.cryptoAlpha = generateAlpha();
		}
	}
	
	/**
	 * Constructor that generates the cryptogram using a a phrase from a custom
	 * file
	 * @param fileLocation
	 */
	public NumberCryptogram (String fileLocation) {
		super(fileLocation);
		if (getPhrase()!=null) {
			this.cryptoAlpha = generateAlpha();
		}
	}
	
	public NumberCryptogram (String phrase, HashMap<Character, String> cryptoAlpha) {
		super(phrase, cryptoAlpha);
		this.cryptoAlpha = cryptoAlpha; 
	}
	
	/**
	 * Randomly generates the alphabet that will be used to create
	 * the puzzle where the alphabet is numbers
	 */
	protected HashMap<Character, String> generateAlpha() {
		Random rnd = new Random();
		int baseIndex, alphaIndex=0;
		HashMap<Character, String> newAlpha = new HashMap<>();
		
		
		do {
			baseIndex = rnd.nextInt(ALPHABET.length())+1;
			if (alphaIndex!=baseIndex) {
				if (!newAlpha.containsKey(ALPHABET.charAt(alphaIndex)) && 
						!newAlpha.containsValue(ALPHABET.charAt(baseIndex-1)+"")) {
					newAlpha.put(ALPHABET.charAt(alphaIndex), baseIndex+"");
					alphaIndex++;
				}
			}
		} while(alphaIndex<ALPHABET.length());
		return newAlpha;
	}
	
	/* ------- Getters ------- */
	
	/**
	 * Takes a number and turns it into the equivalent letter 
	 * in the original alphabet
	 */
	@Override
	public char getPlainLetter(int cryptonumber) {
		for (int i=0; i<ALPHABET.length(); i++) {
			if (cryptoAlpha.get(ALPHABET.charAt(i)).equals(cryptonumber+"")) {
				return ALPHABET.charAt(i);
			}
		}
		return ' ';
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		String toPrint="";
		int wordCounter=0;
		
		for (int i=0; i<getPhrase().length();i++) {
			if (getPhrase().charAt(i)==' ') {
				toPrint = wordCounter%5!=4? toPrint.concat("|"): toPrint.concat("\n");
				wordCounter++;
			} else {
				if (Integer.parseInt(cryptoAlpha.get(getPhrase().charAt(i))) <10) {
					toPrint = toPrint.concat("0");
				}
				toPrint= toPrint.concat(cryptoAlpha.get(getPhrase().charAt(i)));
				if (i+1<getPhrase().length()&&getPhrase().charAt(i+1)!=' ') {
					toPrint = toPrint.concat(",");
				}
			}
			
		}
		
		return toPrint;
	}
}
