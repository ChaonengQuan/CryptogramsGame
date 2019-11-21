import java.util.Map;
import java.util.TreeMap;

/**
 * This class contains the command methods of Cryptograms.
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
 * @author chaonengquan
 *
 */
public class PlayerCommands {

	/**
	 * The Replace method for (replace X by Y) or (X = Y) command
	 * 
	 * First, determine the indexes of the LetterToReplace and Replacement,
	 * 		based on player entered longer version command or shorter verson command.
	 * Second, call replacePlayerGuessedLetter of the LegoBoard to updated the playerGuessedLetters 
	 * 		and playerKeyMap.
	 * Finally, print out the updated playerGuessedLetters and encryptedLetters for visualizaion.
	 * 
	 * @param cmd, the player's input which contains the LetterToReplace and the Replacement
	 * @param myBoard,	the LegoBoard object which needs to be update after replaced the letter
	 * @param playerKeyMap,	the player's decryption mapping which will be updated each time Replace() is called
	 */
	public void Replace(String cmd, LegoBoard myBoard, Map<Character, Character> playerKeyMap) {
		int indexOfLetterToReplace;
		int indexOfReplacement;
		//*******************Determine the index of LetterToReplace and Replacement, based on the command player entered.*******************//
		if (cmd.matches("^replace\\s[A-Z]\\sby\\s[A-Z]$")) {
			indexOfLetterToReplace = 8;
			indexOfReplacement = 13;
		} else {
			indexOfLetterToReplace = 0;
			indexOfReplacement = 4;
		}
		//*******************Call replacePlayerGuessedLetter with letterToReplace and replacement*******************//
		Character letterToReplace = cmd.charAt(indexOfLetterToReplace);
		Character replacement = cmd.charAt(indexOfReplacement);
		myBoard.replacePlayerGuessedLetter(letterToReplace, replacement);
		playerKeyMap.put(letterToReplace, replacement);
		//*******************Prompt player with updated GuessedLetters*******************//
		myBoard.promptPlayerTextWrapping(myBoard.playerGuessedLettersToString(), myBoard.encryptedLettersToString(), 80);
		System.out.println();
	}

	/**
	 * The Freq methond for (freq) command
	 * 
	 * First, store all alphabets with occurence 0 into the new Map, freqMap.
	 * Second, Iterate over the encryptedQuote, every time encouter a letter, update its occurence with +1.
	 * Third, Format print out letter-occurences pair, 7 per line for 4 lines.
	 * 
	 * @param encryptedQuote
	 */
	public void Freq(String encryptedQuote) {
		//*******************Store all alphabets with occurence 0 into the new Map, freqMap.*******************//
		Map<Character, Integer> freqMap = new TreeMap<Character, Integer>();
		char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		for (char C : alphabet) {
			freqMap.put(C, 0);
		}
		//*******************Iterate over the encryptedQuote, every time encouter a letter, update its occurence with +1.*******************//
		for (int i = 0; i < encryptedQuote.length(); i++) {
			char C = encryptedQuote.charAt(i);
			if (!Character.isLetter(C))
				continue;
			freqMap.put(C, freqMap.get(C) + 1);
		}
		//*******************Format print out letter-occurences pair, 7 per line for 4 lines.*******************//
		for (int i = 0; i < alphabet.length; i++) {
			char curC = alphabet[i];
			System.out.printf("%c:%2d ", curC,freqMap.get(curC));
			if ((i + 1) % 7 == 0) {
				System.out.println("\r");
			}
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * The Hint method for (hint) command
	 * 
	 * Display one correct mapping that has not yet been guessed
	 * 
	 * Loop through the value set of the encryptedKeyMap,
	 * if the player has not guessed the current letter yet, pick the current letter as hintLetter 
	 * if the player has guessed the current letter, but not correctly, pick the current letter as hintLetter 
	 * 
	 * use private method getKeyByValue() to find out the corresponding original letter.
	 * Print out the hintLetter, and the original letter.
	 * 
	 * @param encryptedKeyMap, contains the originalLetter-encrypedLetter pairs
	 * @param playerKeyMap,	contains the encrypedLetter-playerGuessedLetter pairs
	 */
	
	public void Hint(Map<Character, Character> encryptedKeyMap, Map<Character, Character> playerKeyMap) {
		Character hintLetter;
		for(Character encryptedlLetter: encryptedKeyMap.values()) {
			//*******************if the player has not guessed the current letter yet*******************//
			if(!playerKeyMap.containsKey(encryptedlLetter)) {
				hintLetter = encryptedlLetter;
				System.out.println("replace " + hintLetter + " by " + getKeyByValue(encryptedKeyMap,hintLetter));
				break;
			//*******************if the player has guessed the current letter, but not correctly*******************//
			}else if(!playerKeyMap.get(encryptedlLetter).equals(getKeyByValue(encryptedKeyMap,encryptedlLetter))) {
				hintLetter = encryptedlLetter;
				System.out.println("replace " + hintLetter + " by " + getKeyByValue(encryptedKeyMap,hintLetter));
				break;
			}
		}
		System.out.println();
	}
	
	/**
	 * A method to find the corresponding key by value.
	 * 
	 * Loop thorugh the keySet, if map.get(key) equals the value
	 * return the key.
	 * 
	 * @param map, the map contanis the key-value pair
	 * @param value, the value of the key-value pair
	 * @return, the key of the corresponding value
	 */
	private Character getKeyByValue(Map<Character,Character> map, Character value) {
		for(Character entry: map.keySet()) {
			if(value.equals(map.get(entry)))
				return entry;	
		}
		return null;
	}
	
	/**
	 * The Exit method for (exit) command
	 * 
	 * call System.exit(0) to exit the program.
	 */
	public void Exit() {
		System.exit(0);
	}
	
	/**
	 * The Help method for (help) command
	 * 
	 * Print out commands, and the functions of each commands.
	 */
	public void Help() {
		System.out.println("i.	replace X by Y – replace letter X by letter Y in our attempted solution\n" + 
				"	X = Y – a shortcut for this same command\n" + 
				"ii.	freq – Display the letter frequencies in the encrypted quotation (i.e., how many of letter X appear) like:\n" + 
				"	A: 3 B: 8 C:4 D: 0 E: 12 F: 4 G: 6\n" + 
				"	(and so on, 7 per line for 4 lines)\n" + 
				"iii.	hint – display one correct mapping that has not yet been guessed\n" + 
				"iv.	exit – Ends the game early\n" + 
				"v.	help – List these commands\n");
	}
	
	/**
	 * The UnknowCommand method for unkown(not supported) commands
	 * 
	 * Print out a message to prompt player to enter a different command.
	 */
	public void UnknowCommand() {
		System.out.println("Unsupported command: enter 'help' for a list of supported commands.");
		System.out.println();
	}

}
