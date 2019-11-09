package models;

public class Player {
	private String name;
	private int wins;
	private int gamesPlayed;
	
	public Player(String name) {
		this.name = name;
		this.wins = 0;
		this.gamesPlayed = 0;
	}
	
	public void incrementWins() {
		wins++;
	}
	
	public void incrementGames() {
		gamesPlayed++;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}	
}
