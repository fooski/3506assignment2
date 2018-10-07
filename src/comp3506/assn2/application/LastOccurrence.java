package comp3506.assn2.application;
import java.lang.Character;

/**
 * A external helper class used for the sole purpose of recording the last occurrence of a character inside the supplied pattern string.
 * 
 * Memory usage: O(1) 
 * 
 * @author Leon Zheng
 *
 */
public class LastOccurrence {
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private int[] accessTable = new int[alphabet.length()];
	
	
	/**
	 * Constructor for the class lastOccurence. Processes the last occurrence of each letter of the alphabet.
	 * 
	 * Run time: O(n) where n is the size of the pattern string
	 * 
	 * @param string the string of the pattern
	 */
	public LastOccurrence(String string) {
		for (int i = 0; i < alphabet.length(); i++) {
			int lastOccurrenceIndex = -1;
			for (int j = 0; j < string.length(); j++) {
				if (string.charAt(j) == alphabet.charAt(i)) {
					lastOccurrenceIndex = j;
				}
			}
			accessTable[i] = lastOccurrenceIndex;
		}
		
	}
	
	
	/**
	 * Returns the index of last occurrence for the designated character
	 * 
	 * Run time: O(1)
	 * 
	 * @param character the character to be checked
	 * @return the index of the last occurrence for character. -1 if character never occurs.
	 */
	public int getOccurence(char character) {
		int index = alphabet.indexOf(Character.toLowerCase(character));
		return accessTable[index];
	}
	

}
