import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Cryptograms is a console based game.
 * 
 * Player will be decrypting a encrypted quote by substituting letters of the
 * quote.
 * 
 * @author chaonengquan
 *
 */
public class Cryptograms {

	public LegoBoard myBoard;
	public PlayerCommands cmdCenter = new PlayerCommands();

	public Scanner scanQuote;
	public String quote = "";
	public Map<Character, Character> encryptedKeyMap = new HashMap<Character, Character>();;
	public Map<Character, Character> playerKeyMap = new HashMap<Character, Character>();

	/**
	 * Main methods that excutes the design logics of the cryptogram
	 * 
	 * First, select random quote from the quote.txt file 
	 * Second, generate the encrypted key based on the slected quote 
	 * Thrid, initialte the LegoBoard, set up the playerGuessedLetters, encryptedLetters, originalLetters 
	 * Fourth, running the decrypting process until the player sucessfully guessed all the
	 * letters.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String args[]) {	
		Cryptograms newGame = new Cryptograms();
		newGame.selectRandomQuote("quotes.txt");
		newGame.generateEncryptKey();
		newGame.initiateLegoBoard();
		newGame.decrypt();
	}

	/**
	 * Take player's input, excutes different methods based on the command player
	 * enter.
	 * 
	 * There are five commands avaliable: 
	 * i.	replace X by Y – replace letter X by letter Y in our attempted solution 
	 * 			X = Y – a shortcut for this same command
	 * ii. 	freq – Display the letter frequencies in the encrypted quotation (i.e., how many of letter X appear) like: 
	 * 			A: 3 B: 8 C:4 D: 0 E: 12 F: 4 G: 6 
	 * 			(and soon, 7 per line for 4 lines) 
	 * iii. hint – display one correct mapping that has not yet been guessed 
	 * iv. 	exit – Ends the game early 
	 * v. 	help – List these commands
	 * 
	 * Finally, if players enter unkonw command, prompt user to enter again.
	 */
	public void commandReceiver(Scanner scan) {
		System.out.println("Enter a command (help to see commands):");
		String cmd = scan.nextLine();

		if (cmd.matches("^replace\\s[A-Z]\\sby\\s[A-Z]$") || cmd.matches("^[A-Z]\\s=\\s[A-Z]$"))
			cmdCenter.Replace(cmd, myBoard, playerKeyMap);
		else if (cmd.equals("freq"))
			cmdCenter.Freq(myBoard.encryptedLettersToString());
		else if (cmd.equals("hint"))
			cmdCenter.Hint(encryptedKeyMap, playerKeyMap);
		else if (cmd.equals("exit"))
			cmdCenter.Exit();
		else if (cmd.equals("help"))
			cmdCenter.Help();
		else
			cmdCenter.UnknowCommand();
	}

	/**
	 * Select a random quote (one line in the file) to be the puzzle.
	 * 
	 * First, use a scanner to scan each line of the file, store them into an
	 * 		ArrayList of String, quoteList. 
	 * Second, call Colletions.shuffle on the quoteList. 
	 * Finally, use List.get() to choose the first line of quote of the
	 * 		shuffled quoteList.
	 * 
	 * @param filename, the name of the file to scan
	 * 
	 */
	public void selectRandomQuote(String filename) {
		List<String> quoteList = new ArrayList<String>();
		try {
			scanQuote = new Scanner(new File(filename));
			while (scanQuote.hasNextLine()) {
				quoteList.add(scanQuote.nextLine().trim());
			}
			Collections.shuffle(quoteList);
			//Store the quote as all uppercases
			quote = quoteList.get(0).toUpperCase();
			scanQuote.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Store all unique letters into a set, map each unique letter with a random
	 * alphabet letter.
	 * 
	 * First, store every Character of the quote, excluding punctuation mark in to a
	 * 		Set of Characters, uniqueQuoteLetters. 
	 * Second, creat an arraylist of 26 alphabets, use Collection.shuffle to shuffle the arraylist, alphabetList.
	 * Third, macth every characters of the uniqueQuoteLetters to each characters of the alphabetList. 
	 * Fourth, if there exist a letters maps to itself, repalce it with the 1st element of alphabetList, 
	 * 		and remove the value added in. Then, add back the old value back into alphabetList. 
	 * Finally, store the matchment as key and value pairs to a HashMap, encryptedKeyMap.
	 */
	public void generateEncryptKey() {
		//*******************Initializing Set of uniqueLetters of the choosen quote.*******************//
		Set<Character> uniqueQuoteLetters = new HashSet<Character>();
		for (char c : quote.toCharArray()) {
			if (Character.isLetter(c))
				uniqueQuoteLetters.add(c);
		}
		//*******************Initializing the Map of encrypted key-value pairs.*******************//
		char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		List<Character> alphabetList = new ArrayList<Character>();
		for (char c : alphabet) {
			alphabetList.add(c);
		}
		Collections.shuffle(alphabetList);
		for (Character C : uniqueQuoteLetters) {
			encryptedKeyMap.put(C, alphabetList.get(0));
			alphabetList.remove(0);
		}
		//*******************Enforcing the constraint that no letter maps to itself.*******************//
		for (Character C : encryptedKeyMap.keySet()) {
			Character currentValue = encryptedKeyMap.get(C);
			if (C.equals(currentValue)) {
				encryptedKeyMap.put(C, alphabetList.get(0));
				alphabetList.remove(0);
				alphabetList.add(currentValue);
			}
		}
	}

	/**
	 * Initiate a instance of LegoBoard object, which is the core object this game
	 * based on, myBoard.
	 * 
	 * Populate myBoard's three fields, with originalLetters, encryptedLetters, and
	 * playerGuessedLetters. playerGuessedLetters is a copy of encryptedLetters,
	 * with all letters replaced with whit spaces, this action is performed in the
	 * constructor of the LegoBoard. 
	 * Finally print out a String representation of the encrypted quote.
	 */
	public void initiateLegoBoard() {
		//*******************Initializing List of originalLetters.*******************//
		List<Character> originalLetters = new ArrayList<Character>();
		char[] quoteLetters = quote.toUpperCase().toCharArray();
		for (char c : quoteLetters) {
			originalLetters.add(c);
		}
		//*******************Initializing List of encryptedLetters.*******************//
		List<Character> encryptedLetters = new ArrayList<Character>(originalLetters);
		for (int i = 0; i < encryptedLetters.size(); i++) {
			Character temp = encryptedLetters.get(i);
			if (encryptedKeyMap.containsKey(temp))
				encryptedLetters.set(i, encryptedKeyMap.get(temp));
			else
				continue;
		}
		//*******************Store both List into myBoard.*******************//
		myBoard = new LegoBoard(originalLetters, encryptedLetters);
		myBoard.promptPlayerTextWrapping(myBoard.playerGuessedLettersToString(), myBoard.encryptedLettersToString(), 80);
	}

	/**
	 * Simulates the processing of decrytion, the program will keep prompting user
	 * to enter commands, untill player has successfully decrypted the original
	 * quote or exit by exit command.
	 * 
	 * Using a while loop to check if playerGuessedLetters and originalLetters of
	 * myBoard are equal, If not equal, keep prompt player to guess. If equal, jump
	 * out the while loop, prompt player "You Got It!", and the game is finished.
	 */
	public void decrypt() {
		while (!myBoard.isDecrypted()) {
			commandReceiver(new Scanner(System.in));
		}
		System.out.println("You Got It!");
	}

}
