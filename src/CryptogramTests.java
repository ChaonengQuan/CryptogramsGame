import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * This Junit testclass tests Cryptograms's functions. 
 * 
 * Test every method of the Cryptograms
 * 
 * @author chaonengquan
 *
 */
class CryptogramTests {
	Cryptograms newGame = new Cryptograms();
	String filename = "ScannerTest.txt";
	String chosen = "Hello!";
	
	/**
	 * Test the selectRandomQuote() method
	 */
	@Test
	public void testSelect() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		String chosen = "Hello!";
		newGame.selectRandomQuote(filename);
		assertEquals(chosen.toUpperCase(),newGame.quote);
	}
	
	/**
	 * Test the generateEncryptKey() method
	 */
	@Test
	public void testGenerate() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		for(char c: chosen.toUpperCase().toCharArray()) {
			if(Character.isLetter(c))
				assertTrue(newGame.encryptedKeyMap.containsKey(c));
		}
	}
	
	/**
	 * Test the initiateLegoBoard method
	 */
	@Test
	public void testInitiate() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		assertFalse(newGame.myBoard.isDecrypted());
	}
	
	/**
	 * Test the replace command.
	 * 
	 * Preset the Scanner value to be replace X by Y, to test the longer version command.
	 */
	@Test
	public void testReplaceLong() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("replace T by A"));
	}
	
	/**
	 * Test the replace command.
	 * 
	 * Preset the Scanner value to be X = Y to test the shorter version command.
	 */
	@Test
	public void testReplaceShort() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("T = A"));
	}
	
	/**
	 * Test the freq command
	 * 
	 * Preset the scanner value to be freq.
	 */
	@Test 
	public void testFred() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("freq"));
	}
	
	/**
	 * test the hint command
	 * 
	 * Preset the scanner value to be hint.
	 */
	@Test 
	public void testHint() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("hint"));
	}
	
	/**
	 * test the help command
	 * 
	 * preset the scanner value to be hint
	 */
	@Test 
	public void testHelp() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("help"));
	}
	
	/**
	 * test unspported command
	 * 
	 * Preset the scanner value to be a random string
	 */
	@Test 
	public void testUnknowCommand() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.commandReceiver(new Scanner("blabla"));
	}
	
	@Test
	public void testWrongMapping() {
		Cryptograms newGame = new Cryptograms();
		String filename = "ScannerTest.txt";
		newGame.selectRandomQuote(filename);
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		
		for(Character c: newGame.encryptedKeyMap.values()) {
			newGame.commandReceiver(new Scanner(c + " = A"));
		}
		newGame.commandReceiver(new Scanner("hint"));
		newGame.myBoard.promptPlayerTextWrapping(newGame.myBoard.playerGuessedLettersToString(), newGame.myBoard.encryptedLettersToString(), 80);
	}
	

	
}
