package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import models.Player;

/**
 * Controller class that manages players in the game.
 * This include: dealing with the process of input and 
 * output to file
 * @author Paul Hutchison, Ross Williamson, Andrew Main
 */
public class Players {
	private final String fileLocation = "res/players.txt";
	
	private ArrayList<Player> playerList;
	private int numberOfPlayers;
	private Player currentPlayer;
	
	/**
	 * Basic constructor that builds a new list of players off
	 * existing ones from a file
	 */
	public Players() {
		playerList = readPlayers();
		if (playerList == null) {
			playerList = new ArrayList<>();
		}
		
		numberOfPlayers = playerList.size();
		if (numberOfPlayers>0) {
			currentPlayer=playerList.get(0);
		}
	}
	
	/**
	 * adds a new player to the game. Returns true if the new player was 
	 * added successfully. Gives the user a character limit and make sure
	 * there doesn't already exist a name like that.
	 * @param name
	 * @return true or false
	 */
	public boolean newPlayer(String name) {
		if (!exists(name) && name.length() > 0 && name.length() < 30) {
			playerList.add(new Player(name));
		} else {
			return false;
		}
		numberOfPlayers++;
		return true;
	}
	
	

	/**
	 * Reads the players from file. If the file is corrupted in parts
	 * and is not of the right formula, then the ones retreived successfully
	 * up until that point will be returned
	 * @return
	 */
	private ArrayList<Player> readPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		Player tempPlayer;
		File f;
		BufferedReader br;
		ArrayList<String> parts = new ArrayList<>();
		String line;
		
		f = new File(fileLocation);
		try {
			br = new BufferedReader(new FileReader(f));
			
			while((line = br.readLine())!=null) {
				parts = new ArrayList<>();
				parts.addAll(Arrays.asList(line.split("\\|")));
				
				tempPlayer = new Player(parts.get(0));
				tempPlayer.setGamesPlayed(Integer.parseInt(parts.get(1)));
				tempPlayer.setWins(Integer.parseInt(parts.get(2)));
				
				players.add(tempPlayer);
			}
			br.close();
			return players;
		} catch(NumberFormatException e) {
			// Take what we already have and play with that
			return players;
		} catch (Exception e) {
			System.out.println("Issue reading file");
		}
		return new ArrayList<>();
	}
	
	/**
	 * Writes the Players details out to a file
	 */
	void writePlayers() {
		File f = new File(fileLocation);
		BufferedWriter bw;
		String toWrite = "";
				
		try {
			bw = new BufferedWriter(new FileWriter(f, false));
			
			for (Player player : playerList) {
				toWrite = toWrite+player.getName()+"|";
				toWrite = toWrite+player.getGamesPlayed()+"|";
				toWrite = toWrite+player.getWins()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether or not a player exists
	 * @param name
	 * @return true if a player exists, otherwise false.
	 */
	public boolean exists(String name) {
		for (int i=0; i<playerList.size();i++) {
			if (playerList.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	/*------- Getters -------*/
	public int getNumber() {
		return numberOfPlayers;
	}
	
	public ArrayList<Player> getPlayers() {
		return playerList;
	}
	
	public Player getPlayer(String name) {
		for (int i=0; i<playerList.size(); i++) {
			if (playerList.get(i).getName().equals(name)) {
				return playerList.get(i);
			}
		}
		return null;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	// Displays the top players in order of win %
	public void getLeaderboard() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList = getPlayers();
		if (playerList.size() == 0) {
			System.out.println("There are no players currently on the leaderboard");
			return;
		}
		ArrayList<Player> leaderboard = new ArrayList<Player>();
		int size = playerList.size();
		
		for (int i = 0; i<size; i++) {
			Player winner = playerList.get(0);
			int winnerIndex = 0;
			for (int e=0; e<playerList.size(); e++) {
				int winnerPerc = winner.getWins()/winner.getGamesPlayed();
				winnerPerc *= 100;
				int playerPerc = playerList.get(e).getWins()/playerList.get(e).getGamesPlayed();
				playerPerc *= 100;
				if (playerPerc >= winnerPerc) {
					winner = playerList.get(e);
					winnerIndex = e;
				}
			}
			leaderboard.add(winner);
			playerList.remove(winnerIndex);
			int finalPerc = winner.getWins()/winner.getGamesPlayed();
			finalPerc *= 100;
			System.out.println(i+1 + ": " + leaderboard.get(i).getName() + " with " +
			Math.round(finalPerc) + "%");
		}
	}
	
}
