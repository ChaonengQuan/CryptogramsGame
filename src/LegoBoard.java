import java.util.ArrayList;
import java.util.List;

/**
 * This is a LegoBoard object, which analogs letter arrangement as lego
 * arrangement on a board.
 * 
 * Each letter is represented as a lego brick. LegoBoard has three
 * ArrayList<Character> fields, playerGuessedLetters, encryptedLetters
 * ,originalLetters. 
 * Visually represented as: 
 * 		playerGuessedLetters	(Top)
 * 		encryptedLetters		(Middle)
 * 		originalLetters		(Bottom, not visiable to player)
 * Example:
 *	 	| | | | | |,| | | | | | |!|
 *	 	|J|R|A|A|P|,| |E|P|T|A|F|!|
 *		|H|E|L|L|O|,| |W|O|R|L|D|!| (Not visiable to player)
 * @author chaonengquan
 *
 */
public class LegoBoard {

	
	private List<Character> playerGuessedLetters;
	private List<Character> encryptedLetters;
	private List<Character> originalLetters;
	private static final int MAX_CHAR_PER_LINE = 80;

	/**
	 * Constructor of LegoBoard, populate LegoBoard's three fields by parameters.
	 * 
	 * playerGuessedLetters is a copy of encryptedLetters, but with all letters
	 * repalced with white spaces.
	 * 
	 * @param originalLetters,  An ArrayList of original quote's letters.
	 * @param encryptedLetters, An ArrayList of encrypted quote's letters.
	 */
	public LegoBoard(List<Character> originalLetters, List<Character> encryptedLetters) {
		setOriginalLetters(originalLetters);
		setEncryptedLetters(encryptedLetters);
		initiatePlayerGuessedLetters();
	}

	/**
	 * Initiate playerGuessedLetters.
	 * 
	 * Copy encryptedLetters, replace every letter with white space, leave
	 * punctuation marks uncahnged. Store the new list as playerGuessedLetters.
	 */
	private void initiatePlayerGuessedLetters() {
		playerGuessedLetters = new ArrayList<Character>(encryptedLetters);
		for (int i = 0; i < playerGuessedLetters.size(); i++) {
			if (Character.isLetter(playerGuessedLetters.get(i)))
				playerGuessedLetters.set(i, new Character(' '));
		}
	}

	/**
	 * Replace all occurrences of Character oldC, with the new Character newC.
	 * 
	 * First determine if the oldC is punctuation mark, if so, perform no action.
	 * Then if the encryptedLetters contains oldC, using List.set() method to repalce it with newC.
	 * 
	 * @param oldC, the Chracter to repalce
	 * @param newC, replacement
	 */
	public void replacePlayerGuessedLetter(Character oldC, Character newC) {
		if (Character.isLetter(oldC)) {
			for (int i = 0; i < playerGuessedLetters.size(); i++) {
				Character currentC = encryptedLetters.get(i);
				if (currentC.equals(oldC))
					playerGuessedLetters.set(i, newC);
			}
		}
	}

	/**
	 * Determine if the quote has been decrypted.
	 * 
	 * Simply check if originalLetters equals playerGuessedLetters.
	 * Return true, if playerGuessedLetters contain the same elements of
	 * originalLetters in the same order.
	 * Other wise, return false.
	 * 
	 * @return if originalLetters equals playerGuessedLetters.
	 */
	public boolean isDecrypted() {
		return originalLetters.equals(playerGuessedLetters);
	}

	/**
	 * Return a string representation of the playerGuessedLetters.
	 * 
	 * Using a stringbuilder to append every character in the playerGuessedLetters,
	 * return the string of the stringbuilder.
	 * 
	 * @return a string consisting of exactly this sequence of characters
	 */
	public String playerGuessedLettersToString() {
		StringBuilder sb = new StringBuilder();
		for (Character C : playerGuessedLetters) {
			sb.append(C);
		}
		return sb.toString();
	}

	/**
	 * Return a string represtation of the encryptedLetters.
	 * 
	 * Using a stringbuilder to append every character in the playerGuessedLetters,
	 * return the string of the stringbuilder.
	 * 
	 * @return a string consisting of exactly this sequence of characters
	 */
	public String encryptedLettersToString() {
		StringBuilder sb = new StringBuilder();
		for (Character C : encryptedLetters) {
			sb.append(C);
		}
		return sb.toString();
	}
	
	/**
	 * Prompt the user with playerGuessedLetters on top, encryptedLetters on bot, with text wrapping.
	 * 
	 * Each line will contain exact less that 80 chars.
	 * Only display them such that they fit appropriately 
	 * (break each at whitespace or punctuation so that each part does not exceed 80 characters).
	 * @param player, the playerGuessed letters
	 * @param encrypt, the encrypted letters
	 * @param max_chars, the max char each line
	 */
	public void promptPlayerTextWrapping(String player, String encrypt, int max_chars) {
		int index = 0;
		while (player.length() - index > max_chars) {
			int endIndex = index + max_chars;
			while (Character.isLetter(encrypt.charAt(endIndex))) {	
				endIndex--;
			}
			System.out.println(player.substring(index, endIndex));
			System.out.println(encrypt.substring(index, endIndex));
			System.out.println();
			index = endIndex + 1;
		}
		//Last Line
		System.out.println(player.substring(index, player.length()));
		System.out.println(encrypt.substring(index, encrypt.length()));
	}

	/**
	 * Setter method for encryptedLetters
	 * @param encryptedLetters the encryptedLetters to set
	 */
	public void setEncryptedLetters(List<Character> encryptedLetters) {
		this.encryptedLetters = encryptedLetters;
	}

	/**
	 * Setter method for originalLetters
	 * @param encryptedLetters the encryptedLetters to set
	 */
	public void setOriginalLetters(List<Character> originalLetters) {
		this.originalLetters = originalLetters;
	}

}
