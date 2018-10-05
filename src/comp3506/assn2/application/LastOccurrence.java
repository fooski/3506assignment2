package comp3506.assn2.application;
import java.lang.Character;

/**
 * A external helper class used for the sole purpose of recording the last occurrence of a character inside the supplied pattern string.
 * 
 * Memory usage: O(n) n being the size of the pattern string
 * 
 * @author Leon Zheng
 *
 */
public class LastOccurrence {
	private String string;
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private int[] accessTable;
	
	
	/**
	 * Constructor for the class lastOccurence. Processes the last occurrence of each letter of the alphabet.
	 * 
	 * Run time: O(n) where n is the size of the pattern string
	 * 
	 * @param string the string of the pattern
	 */
	public LastOccurrence(String string) {
		this.string = string;
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
	
	
	public int getOccurence(char character) {
		int index = alphabet.indexOf(Character.toLowerCase(character));
		return accessTable[index];
	}
	

}
