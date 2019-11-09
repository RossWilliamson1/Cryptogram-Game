package tests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controllers.Players;

/**
 * Test various things associated with the player manager
 * @author Paul Hutchison
 */
class PlayersTest {

	/**
	 * Test to see whether or not one can add a player to the game
	 */
	@Test
	void AddPlayerTest() {
		Players ply = new Players();
		int originalNumber = ply.getNumber();
		
		ply.newPlayer("Steve");
		
		assertTrue(ply.getNumber()>originalNumber);
	}
	
	/**
	 * Test to see if the correct result is acheived when the getPlayer
	 * function is used
	 */
	@Test
	void GetPlayerTest() {
		Players ply = new Players();
		ply.newPlayer("Adam");
		
		// Check to see that the getPlayer function can return a player object
		assertNotNull(ply.getPlayer("Adam"));
		
		// Check to see if the getPlayer function will return null when
		//	given rubbish
		assertNull(ply.getPlayer("asdfasdfasdfasdf"));
	}
	
	/**
	 * Test to check whether the boundaries of adding a new player
	 */
	@Test
	void NewPlayerTest() {
		Players ply = new Players();
		
		// Check the lower boundary
		assertFalse(ply.newPlayer(""));
		
		// Check the higher boundary
		assertFalse(ply.newPlayer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		
		// Check whether or not a correct name can be added
		assertTrue(ply.newPlayer("Steve"));
		
		// Check to see if we can add duplicates of the same name
		assertFalse(ply.newPlayer("Steve"));
	}
	@Test
	void LeaderboardTest() {
		Players ply = new Players();
		ply.getLeaderboard();
	}
}
