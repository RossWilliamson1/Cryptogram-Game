package controllers;

import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import models.Cryptogram;
import models.Player;

/**
 * 
 * @author Paul Hutchinson, Ross Williamson, Andrew Main and Monika Kornaszewska
 *
 */
public class CryptogramDriver {

	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		Players players = new Players();
		GramController game = new GramController();
		
		// Get the user name
		players = getPlayer(players, in);
		System.out.println();
		
		do {
			if (game.getUsersWithSaves().contains(players.getCurrentPlayer().getName())) {
				if (getLoad(in)) {
					if (!game.restoreGame(players.getCurrentPlayer().getName())) {
						continue; // If can't restore game then start again 
					}					
				} else {
					game.newGame(players.getCurrentPlayer().getName(), getGameType(in));
					players.getCurrentPlayer().setGamesPlayed(
							players.getCurrentPlayer().getGamesPlayed()+1);
				}
			} else {
				game.newGame(players.getCurrentPlayer().getName(), getGameType(in));
				players.getCurrentPlayer().setGamesPlayed(
						players.getCurrentPlayer().getGamesPlayed()+1);
			}
			players.writePlayers();
			playGame(in, players, game);
		} while(getContinuePlaying(in));
		System.out.println("Goodbye!");
	}
	
	
	/**
	 * 
	 * @param in
	 * @param players
	 * @param game
	 */
	private static void playGame(Scanner in, Players players, GramController game) {
		boolean complete = false;
		int whatPlace = 0;
		String input;

		
		while (!complete) {
			System.out.println(game.getGram());
			printDisplay(game.getWorkingPhrase(), whatPlace);
			System.out.println("List commands by entering: ~");
			System.out.print("What would you like to guess: ");
			input = in.nextLine().toLowerCase();
			
			switch (input) {
			case "#": // Move the cursor Forward
				if (whatPlace >= game.getWorkingPhrase().length() - 1) {
					whatPlace = 0;
				} else {
					whatPlace = game.getWorkingPhrase().charAt(whatPlace + 1) == ' ' ? 
							whatPlace + 2 : whatPlace + 1;
				}
				break;
			case ".#":
				if (whatPlace == 0) {
					whatPlace = (game.getWorkingPhrase().length() - 1);
				} else {
					whatPlace = game.getWorkingPhrase().charAt(whatPlace - 1) == ' ' ? 
							whatPlace - 2 : whatPlace - 1;
				}
				break;
			case ".": game.undoLetter(whatPlace); break;
			case "~": getHelp(in); break;
			case "$": game.saveGame(); break;
			case "%": printFrequencies(in, game); break;
			case "stat": game.getStats(players); break;
			case "sol": System.out.println("The solution is: \n" + game.getGram().getPhrase()); return;
			case "exit": return;
			case "hint":
				Random r = new Random();
				int random = 0;
				while (game.getWorkingPhrase().charAt(random) != '#') {
					if (game.getWorkingPhrase().charAt(random) != game.getGram().getPhrase().charAt(random))
						break;
					random = r.nextInt(game.getWorkingPhrase().length());
					
				}
				int temp = whatPlace;
				whatPlace = random;
				char showHint = game.getGram().getPhrase().charAt(whatPlace);
				game.changePhrase(Character.toString(showHint), whatPlace);
				whatPlace = temp;
				break;
			
			case "leader": players.getLeaderboard(); break;
				
			default:
				if (input.length()==1) {
					game.changePhrase(input, whatPlace);
					game.updateGuesses(input, whatPlace);
				} else {
					System.out.println("=============\nInvalid entry\n=============");
				}
			}

			// Find out if the user has won the game
			if (game.getWorkingPhrase().equals(
					game.getGram().getPhrase())) {
				System.out.println(game.getGram().getPhrase());
				System.out.println("You're a winner!");
				players.getCurrentPlayer().setWins(
						players.getCurrentPlayer().getWins()+1);
				players.writePlayers();
				complete = true;
			}

		}
	}

	/**
	 * This ^ acts as a cursor for the user moving through the letters
	 * 
	 * @param phrase
	 * @param gram
	 * @param whatPlace
	 */
	private static void printDisplay(String phrase, int whatPlace) {
		System.out.println(phrase);
		for (int i = 0; i < whatPlace; i++) {
			System.out.print(" ");
		}
		System.out.println("^");
	}

	/**
	 * Gets user input to choose between Letters or numbers
	 * 
	 * @param in
	 * @return
	 */
	private static boolean getGameType(Scanner in) {
		String tempStr;

		System.out.print("Would you like to play a game with letters or numbers? (L or N) : ");
		tempStr = in.nextLine().toLowerCase();
		System.out.println();

		if (tempStr.equals("l")) {
			return true;
		} else if (tempStr.toLowerCase().equals("n")) {
			return false;
		} else {
			return getGameType(in);
		}
	}
	
	private static boolean getContinuePlaying(Scanner in) {
		String tempStr;
		System.out.print("Would you like to play another game? (Y or N) : ");
		tempStr = in.nextLine().toLowerCase();
		
		if (tempStr.equals("y")) {
			return true;
		} else if (tempStr.equals("n")) {
			return false;
		} else {
			return getContinuePlaying(in);
		}
	}

	/**
	 * 
	 * @author Andrew Main
	 * @param in
	 * @return
	 */
	private static boolean getLoad(Scanner in) {
		String tempStr;

		System.out.print("Shall I load a save? (Y or N) : ");
		tempStr = in.nextLine().toLowerCase();
		System.out.println();

		if (tempStr.toLowerCase().equals("y")) {
			return true;
		} else if (tempStr.toLowerCase().equals("n")) {
			return false;
		} else {
			return getLoad(in);
		}
	}
	
	private static Players getPlayer (Players players, Scanner in) {
		String username;
		System.out.print("What up? I am your Cryptogram Controller. What is your name? : ");
		username = in.nextLine().toLowerCase();
		
		if (!players.newPlayer(username)) {
			if (username.length()>0 && username.length()<30) { // Load existing user
				players.setCurrentPlayer(players.getPlayer(username));
				return players;
			} else {
				System.out.println("Invalid Input : Please enter a name < 30 characters long");
				return getPlayer(players, in);
			}
		} 
		System.out.println("Welcome new user");
		players.setCurrentPlayer(players.getPlayer(username));
		return players;
	}
	
	private static void getHelp (Scanner in) {
		System.out.print("  #    <- Move cursor forward\n"+
				"  .#     <- Move cursor backward\n"+
				"  .      <- Undo letter\n"+
				"  ~      <- Show help\n"+
				"  sol    <- Show solution\n"+
				"  stat   <- Show stats\n"+
				"  $      <- Save game\n"+
				"  %      <- Print frequencies\n"+
				"  hint   <- Get a hint\n"+ 
				"  leader <- View leaderboard\n"+
				"  exit <- Exit game\n"+
				"Press enter to continue. ");
		in.nextLine();
	}
	
	private static void printFrequencies (Scanner in, GramController game) {
		String line[] = {"","",""};
		int totalLetters=0;
		double percentage=0;
		HashMap<Character, Integer> frequencies 
			= game.getGram().getFrequencies();
		
		// Count total letters
		for (int i=0; i<game.getGram().getPhrase().length(); i++) {
			if (game.getGram().getPhrase().charAt(i)!=' ') {
				totalLetters++;
			}
		}
		
		for (int i=0; i<Cryptogram.ALPHABET.length(); i++) {
			if (frequencies.get(Cryptogram.ALPHABET.charAt(i))!=0) {
				line[0]+=Cryptogram.ALPHABET.charAt(i)+"\t";
				line[1]+=frequencies.get(Cryptogram.ALPHABET.charAt(i))+"\t";
				percentage = (double)frequencies.get(Cryptogram.ALPHABET.charAt(i));
				percentage /= totalLetters;
				line[2]+=Double.parseDouble(String.format("%.2f", percentage*100))+"%\t";
			}
		}
		
		for (String aLine:line) {
			System.out.println(aLine);
		}
		System.out.println("Press enter to continue. ");
		in.nextLine();
	}
		
}
