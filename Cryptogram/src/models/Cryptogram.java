package models;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Random;

/**
 * 
 * @author Paul Hutchison
 *
 */
public class Cryptogram {
	private final int NUM_QUOTES = 15;
	private final String BASE_QUOTES_FILE = "res/phrases.txt";
	
	private String phrase;
	private HashMap<Character, Integer> frequencies;
	public HashMap<Character, String> cryptoAlpha;
	
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	
	
	protected Cryptogram () {
		this.phrase= generatePhrase(BASE_QUOTES_FILE);
		if (phrase!=null) {
			frequencies= generateFrequencies();
		}
	}
	
	protected Cryptogram (String fileLocation) {
		this.phrase= generatePhrase(fileLocation);
		if (phrase!=null) {
			frequencies= generateFrequencies();
		}
	}
	
	protected Cryptogram (String phrase, HashMap<Character, String> cryptoAlpha) {
		this.phrase = phrase;
		frequencies = generateFrequencies();
	}
	
	// Methods
	/**
	 * This gets a list of phrases from a file. It then randomly selects one
	 * of these phrases as the playing cryptogram
	 * @param fileLocation
	 * @return a <b>phrase</b> OR it will close the program
	 */
	protected String generatePhrase(String fileLocation) {
		String[] phrases = new String[NUM_QUOTES];
		String tempStr;
		Random rnd = new Random();
		File file = new File(fileLocation);
		BufferedReader br;
		int numPhrases = 0;
		
		try {
			br = new BufferedReader(new FileReader(file));
			while(numPhrases < NUM_QUOTES && 
					(tempStr=br.readLine())!=null) {
				phrases[numPhrases]=tempStr.trim().toLowerCase();
				numPhrases++;
			}
			br.close();
			return phrases[rnd.nextInt(numPhrases)];
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error reading phrases");
		} catch (Exception e) {
			System.out.println(e);
		} 
		// Only called when there is an error getting the phrase
		return null; // Not actually reached but java complains
	}
	
	/**
	 * This function returns a hashmap of the frequency of the elements
	 * of each letter in the phrase.
	 * This can be accessed by:
	 * {cryptogram}.getFrequencies().get({Character you want the frequency for}) 
	 * @return freq
	 */
	private HashMap<Character, Integer> generateFrequencies() {
		HashMap<Character,Integer> freq = new HashMap<>();
		int counter=0;
		
		for (int i=0; i<LetterCryptogram.ALPHABET.length(); i++) {
			for (int j=0; j<phrase.length(); j++) {
				if (LetterCryptogram.ALPHABET.charAt(i)==phrase.charAt(j)) {
					counter++;
				}
			}
			freq.put(LetterCryptogram.ALPHABET.charAt(i), counter);
			counter=0;
		}
		
		
		return freq;
	}
	
	// Getters and Setters
	public String getPhrase() {
		return phrase;
	}
	
	public HashMap<Character, Integer> getFrequencies() {
		return frequencies;
	}

	public HashMap<Character, String> getCryptoAlpha() {
		return cryptoAlpha;
	}
	
	
	 /* Implemented in the sub-classes */
	public char getPlainLetter(char cryptoletter) {
		return ' ';
	}
	
	public char getPlainLetter(int cryptonumber) {
		return 0;
	}
}
