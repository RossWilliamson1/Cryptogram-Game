package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controllers.GramController;

class GramControllerTest {

	@Test
	void createGramTest() {
		GramController game = new GramController();
		// Make sure that the cryptogram gets initialised to null
		//		This is so it can be set later
		assertNull(game.getGram());
		
		// After a new game is made, one can make a 
		//		cryptogram
		game.newGame("sandy", true);
		assertNotNull(game.getGram());
		
		// Can update guesses
		game.updateGuesses("l", 0);
		game.updateGuesses("m", 1);
		game.updateGuesses("n", 2);
		game.updateGuesses("o", 3);
		
		assertEquals(game.getCorrect()+game.getWrong(), 4);
		
	}
	
	@Test
	void addGuessesTest() {
		GramController game = new GramController();
		game.newGame("sandy", true);
		
		// Can update guesses
		game.updateGuesses("l", 0);
		game.updateGuesses("m", 1);
		game.updateGuesses("n", 2);
		game.updateGuesses("o", 3);
		
		assertEquals(game.getCorrect()+game.getWrong(), 4);
	}

}
