package models;

import java.util.HashMap;

public class CryptogramGame {
	
	private String playerName;
	private int lettersWrong, lettersRight;
	private String realPhrase;
	private String attempt;
	private boolean lettersGame;
	private HashMap <Character, String> cryptoAlpha;

	public CryptogramGame() {
		playerName="";
		lettersWrong=0;
		lettersRight=0;
		realPhrase="";
		lettersGame=true;
		cryptoAlpha=new HashMap<>();
				
	}
	
	/* Getters and setters*/
	public String getPlayerName() {
		return playerName;
	}
	public int getLettersWrong() {
		return lettersWrong;
	}
	public int getLettersRight() {
		return lettersRight;
	}
	public String getRealPhrase() {
		return realPhrase;
	}
	public boolean isLettersGame() {
		return lettersGame;
	}
	public HashMap<Character, String> getCryptoAlpha() {
		return cryptoAlpha;
	}
	public String getAttempt() {
		return attempt;
	}
	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public void setLettersWrong(int gamesPlayed) {
		this.lettersWrong = gamesPlayed;
	}
	public void setLettersRight(int gamesWon) {
		this.lettersRight = gamesWon;
	}
	public void setRealPhrase(String realPhrase) {
		this.realPhrase = realPhrase;
	}
	public void setLettersGame(boolean lettersGame) {
		this.lettersGame = lettersGame;
	}
	public void setCryptoAlpha(HashMap<Character, String> cryptoAlpha) {
		this.cryptoAlpha = cryptoAlpha;
	}
}
