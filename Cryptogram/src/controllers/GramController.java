package controllers;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import models.Cryptogram;
import models.CryptogramGame;
import models.LetterCryptogram;
import models.NumberCryptogram;

/**
 * Class that deals with the cryptogram and all that comes along with.
 * This include loading, saving, undoing letters and more.
 * @author Paul Hutchison, Andrew Main, and Ross Williamson
 */
public class GramController {
	private static final String SAVE_LOCATION= "res/saves.txt"; 
	
	private CryptogramGame gameDetails;
	private Cryptogram gram;
	private ArrayList<String> allSaves;
	private ArrayList<String> usersWithSaves;
	
	/**
	 * Basic constructor giving everything an initial value
	 */
	public GramController (){
		allSaves = loadSaves();
		usersWithSaves = getSavedUser();
		gameDetails = new CryptogramGame();
		gram = null;
	}
	
	/* Local Methods */
	/**
	 * Returns a list of users that can be loaded successfully from file
	 * @return names of users successfully loaded from save file
	 */
	private ArrayList<String> getSavedUser() {
		ArrayList<String> names = new ArrayList<>();
		
		for (int i=0; i<allSaves.size(); i++) {
			names.add(allSaves.get(i).split("\\|")[0]);
		}
		return names;
	}
	
	/**
	 * If the player has a valid save then fill in the game data with
	 * the appropriate values. Otherwise give out a null or warning 
	 * for the user.
	 * @param saves
	 * @param name
	 * @return save file data
	 */
	private CryptogramGame restoreSaveData(ArrayList<String> saves, String name) {
		int playerSaveNumber=-1;
		boolean found=true;
		ArrayList<String> parts, alpha;
		CryptogramGame loadedGame = new CryptogramGame();
				
		for (int i=0; i<saves.size(); i++) {
			if (usersWithSaves.get(i).equals(name)) {
				playerSaveNumber=i;
				break;
			}
			if (playerSaveNumber==-1&&i+1==saves.size()) {
				found=false;
			}
		}
				
		if (found) {
			parts = new ArrayList<>(Arrays.asList(saves.get(playerSaveNumber).split("\\|")));
			try {
				loadedGame.setPlayerName(parts.get(0));
				loadedGame.setLettersWrong(Integer.parseInt(parts.get(1)));
				loadedGame.setLettersRight(Integer.parseInt(parts.get(2)));
				loadedGame.setLettersGame(Boolean.parseBoolean(parts.get(3)));
				loadedGame.setRealPhrase(parts.get(4));
				loadedGame.setAttempt(parts.get(5));
				alpha = new ArrayList<>(Arrays.asList(parts.get(6).split(":")));
				if (alpha.size()==26) {
					for (int i=0; i<models.Cryptogram.ALPHABET.length(); i++) {
						loadedGame.getCryptoAlpha().put(models.Cryptogram.ALPHABET.charAt(i), alpha.get(i));
					}
				}else {
					throw new Exception();
				}
				return loadedGame;
			} catch(Exception e) {
				System.out.println("Your save appears to have been corruted");
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Get all of the lines for a file
	 * @return Arraylist of lines (potential saves)
	 */
	private ArrayList<String> loadSaves() {
		ArrayList<String> saves = new ArrayList<>();
		String tempLine;
		File f = new File(SAVE_LOCATION);
		BufferedReader br;
		
		try {
			f.createNewFile();
			
			br = new BufferedReader(new FileReader(f));
			
			while ((tempLine = br.readLine())!=null) {
				if (Arrays.asList(tempLine.split("\\|")).size()==7) {
					saves.add(tempLine);
				} else {
					System.out.println("Issue reading file. May have lost save data");
				}
			}
		} catch (Exception e) {
			System.out.println("Issue reading file");
		}
		return saves;
	}
	
	/* Global Methods */
	/**
	 * takes the game data and turns it into a playable game
	 * @param name
	 * @return boolean if the game has been restored properly
	 */
	public boolean restoreGame(String name) {
		gameDetails = restoreSaveData(allSaves, name.toLowerCase());
		if (gameDetails!=null) {
			if (gameDetails.isLettersGame()) {
				gram = new LetterCryptogram(
						gameDetails.getRealPhrase(),
						gameDetails.getCryptoAlpha());
			} else {
				gram = new NumberCryptogram(
						gameDetails.getRealPhrase(),
						gameDetails.getCryptoAlpha());
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Created new game with basic game data
	 * @param name
	 * @param playLetters
	 */
	public void newGame (String name, boolean playLetters) {
		if (playLetters) {
			gram = new LetterCryptogram();
		} else {
			gram = new NumberCryptogram();
		}
		
		gameDetails.setPlayerName(name.toLowerCase());
		gameDetails.setLettersGame(playLetters);
		gameDetails.setRealPhrase(gram.getPhrase());
		gameDetails.setAttempt(gram.getPhrase().replaceAll("[a-z]", "#"));
		gameDetails.setCryptoAlpha(gram.getCryptoAlpha());
		
	}
	
	/**
	 * Gets rid of a letter
	 * @param whichLetter
	 */
	public void undoLetter (int whichLetter) {
		changePhrase("#", whichLetter);
		System.out.println("Undone!");
	}
	
	/**
	 * This changes the output phrase adding the new letter
	 * @param input
	 * @param whatPlace
	 */
	public void changePhrase(String input, int whatPlace) {
		String change = "";

		for (int i = 0; i < gram.getPhrase().length(); i++) {
			if (gram.getPhrase().charAt(i) == gram.getPhrase().charAt(whatPlace)) {
				change = change.concat(input);
			} else {
				change = change.concat(getWorkingPhrase().charAt(i) + "");
			}
		}

		gameDetails.setAttempt(change);
	}
	
	/**
	 * Saves the game to a file in a specific format that it
	 * can be read again.
	 */
	public void saveGame() {
		File f = new File(SAVE_LOCATION);
		BufferedWriter bw;
		String toSave="", alpha="";
		
		//Remove the player from the things to save
		for (int i=0; i<allSaves.size(); i++) {
			if (allSaves.get(i).split("\\|")[0]
					.equals(gameDetails.getPlayerName())) {
				allSaves.remove(i);
			}
		}
		
		// Create the crypto alphabet with colon delimiters
		for (int i=0; i<Cryptogram.ALPHABET.length();i++) {
			alpha+=gameDetails.getCryptoAlpha().values().toArray()[i];
			if (i+1 != Cryptogram.ALPHABET.length()) {
				alpha+=":";
			}
		}
		
		// Form output
		toSave += gameDetails.getPlayerName()+"|";
		toSave += gameDetails.getLettersWrong()+"|";
		toSave += gameDetails.getLettersRight()+"|";
		toSave += gameDetails.isLettersGame()+"|";
		toSave += gameDetails.getRealPhrase()+"|";
		toSave += gameDetails.getAttempt()+"|";
		toSave += alpha+"\n";
		
		// Rewrite all saves to file
		try {
			bw = new BufferedWriter(new FileWriter(f, false));
			
			allSaves.add(toSave);
			for (String save: allSaves) {
				bw.write(save);
			}
			bw.close();
		}catch(Exception e) {
			System.out.println("Issue saving file");
		}
	}
	
	/**
	 * Prints interesting stats about the game and the player
	 * @param players
	 */
	public void getStats (Players players) {
		double percentage=0;
		int correct, incorrect;
		
		correct=gameDetails.getLettersRight();
		incorrect=gameDetails.getLettersWrong();
		
		if (incorrect==0) {
			System.out.println("Your number of correct guesses as a percentage is: 100%");
			System.out.println("Number of correct guesses: " + correct);
			System.out.println("Number of incorrect guesses: " + incorrect);
		} 
		else {
			percentage = correct + incorrect;
			percentage = (double)correct / percentage;
			percentage = percentage * 100;
			System.out.println("Your number of correct guesses as a percentage is: " + 
			Math.round(percentage) + "%");
			System.out.println("Number of correct guesses: " + correct);
			System.out.println("Number of incorrect guesses: " + incorrect);
		}
		System.out.println("\t\t------");
		if (players.getCurrentPlayer().getGamesPlayed()>0) {
			System.out.println("Cryptogram win percentage : "+
					Math.round(((double)(players.getCurrentPlayer().getWins()/
								(double)players.getCurrentPlayer().getGamesPlayed()) *100))+"%");
		} else {
			System.out.println("Cryptogram win percentage : 100%");
		}
	}
	
	/**
	 * Updates the two variables used in calculating the amount
	 * of characters the user has guessed right. These values
	 * are used in calculating percentages
	 * @param guess
	 * @param cursor
	 */
	public void updateGuesses(String guess, int cursor) {
		if (getGram().getPhrase().charAt(cursor)==guess.charAt(0)){
			gameDetails.setLettersRight(gameDetails.getLettersRight()+1);
		} else {
			gameDetails.setLettersWrong(gameDetails.getLettersWrong()+1);
		}
	}
	
	/* Getters */
	
	public int getCorrect() {
		return gameDetails.getLettersRight();
	}
	
	public int getWrong() {
		return gameDetails.getLettersWrong();
	}
	
	public ArrayList<String> getUsersWithSaves() {
		return usersWithSaves;
	}
	public String getWorkingPhrase() {
		return gameDetails.getAttempt();
	}

	public Cryptogram getGram() {
		return gram;
	}
}
