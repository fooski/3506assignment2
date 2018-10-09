package comp3506.assn2.application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import comp3506.assn2.application.ArrayMap.MapIterator;
import comp3506.assn2.application.ArrayMap.MapNode;
import comp3506.assn2.utils.Pair;
import comp3506.assn2.utils.Triple;
import java.lang.Object;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.math.*;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the constructor stub below and override the methods from the Search interface
 * so that they call the necessary code in your application.
 * 
 * @author 
 */
public class AutoTester implements Search {
	private String content;
	private ArrayMap stringTable;
	private int numberOfLines = 0;

	/**
	 * Create an object that performs search operations on a document.
	 * If indexFileName or stopWordsFileName are null or an empty string the document should be loaded
	 * and all searches will be across the entire document with no stop words.
	 * All files are expected to be in the files sub-directory and 
	 * file names are to include the relative path to the files (e.g. "files\\shakespeare.txt").
	 * 
	 * @param documentFileName  Name of the file containing the text of the document to be searched.
	 * @param indexFileName     Name of the file containing the index of sections in the document.
	 * @param stopWordsFileName Name of the file containing the stop words ignored by most searches.
	 * @throws FileNotFoundException if any of the files cannot be loaded. 
	 *                               The name of the file(s) that could not be loaded should be passed 
	 *                               to the FileNotFoundException's constructor.
	 * @throws IllegalArgumentException if documentFileName is null or an empty string.
	 */
	public AutoTester(String documentFileName, String indexFileName, String stopWordsFileName) 
			throws FileNotFoundException, IllegalArgumentException {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(documentFileName));
			content = new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new FileNotFoundException();
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(documentFileName), StandardCharsets.UTF_8))) {
			while (reader.readLine() != null) {
				numberOfLines++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stringTable = new ArrayMap(documentFileName, numberOfLines);
	}

	/**
	 * Determines the number of times the word appears in the document.
	 * 
	 * Run time: O(N), N = n*m (n = size of text file and m = size of word string)
	 * 
	 * @param word The word to be counted in the document.
	 * @return The number of occurrences of the word in the document.
	 * @throws IllegalArgumentException if word is null or an empty String.
	 */
	@Override
	public int wordCount(String word) throws IllegalArgumentException {
		int count = 0;
		int length = word.length();
		OccurrenceTable occurrence;
		int i = length - 1, j = length - 1;
		if (length == 0 || word.equals(null)) {
			throw new IllegalArgumentException();
		}
		occurrence = new OccurrenceTable(word);
		while (i < content.length() - 1) {
			if (Character.toLowerCase(content.charAt(i)) == Character.toLowerCase(word.charAt(j))) { //if matches
				if (j == 0) {
					if (!Character.isAlphabetic(content.charAt(i - 1)) && !Character.isAlphabetic(content.charAt(i + length))) { //end of word seperated by space
						count++;
					} 
					i = i + 2 * length - 1;
					j = length - 1;
				} else {
					i--;
					j--;
				}
			} else {
				char character = content.charAt(i);
				int lastOccurrence = occurrence.getOccurence(character);
				i = i + length - Math.min(j, 1 + lastOccurrence);
				j = length - 1;
			}
		}
		return count;
	}
	
	
	/**
	 * Finds all occurrences of the phrase in the document.
	 * A phrase may be a single word or a sequence of words.
	 * 
	 * Run time: O(N), N = n*m (n = size of text file and m = size of word string)
	 * 
	 * @param phrase The phrase to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the phrase.
	 *         Returns an empty list if the phrase is not found in the document.
	 * @throws IllegalArgumentException if phrase is null or an empty String.
	 */
	@Override
	public List<Pair<Integer, Integer>> phraseOccurrence(String phrase) throws IllegalArgumentException {
		List<Pair<Integer, Integer>> result = new LinkedList<Pair<Integer, Integer>>();
		if (phrase.isEmpty() || phrase.equals(null)) {
			throw new IllegalArgumentException();
		}
		MapIterator iterator = stringTable.getIterator();
		while (iterator.hasNext()) {
			MapNode lineNode = iterator.next();
			int lineNumber = lineNode.getLineNumber();
			String lineString = lineNode.getLineContent();
			int column = boyerMoore(lineString, phrase);
			if (column != -1) {
				Pair<Integer, Integer> pair = new Pair<Integer, Integer>(lineNumber, column);
				result.add(pair);
			}
		}
		return result;
	}

	/**
	 * Finds all occurrences of the prefix in the document.
	 * A prefix is the start of a word. It can also be the complete word.
	 * For example, "obscure" would be a prefix for "obscure", "obscured", "obscures" and "obscurely".
	 * 
	 * Run time: O(
	 * 
	 * @param prefix The prefix of a word that is to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the prefix.
	 *         Returns an empty list if the prefix is not found in the document.
	 * @throws IllegalArgumentException if prefix is null or an empty String.
	 */
	@Override
	public List<Pair<Integer, Integer>> prefixOccurrence(String prefix) throws IllegalArgumentException {
		List<Pair<Integer, Integer>> result = new LinkedList<Pair<Integer, Integer>>();
		if (prefix.isEmpty() || prefix.equals(null)) {
			throw new IllegalArgumentException();
		}
		MapIterator iterator = stringTable.getIterator();
		while (iterator.hasNext()) {
			int i = prefix.length() - 1, j = prefix.length() - 1;
			OccurrenceTable occurrence = new OccurrenceTable(prefix);
			MapNode lineNode = iterator.next();
			int lineNumber = lineNode.getLineNumber();
			String lineString = lineNode.getLineContent();
			if (prefix.length() < lineString.length()) { //only process if the phrase is smaller than this line
				while (i < lineString.length() - 1) {
					if (Character.toLowerCase(lineString.charAt(i)) == Character.toLowerCase(prefix.charAt(j))) { //if matches
						if (j == 0) {
							if (!Character.isAlphabetic(lineString.charAt(i - 1))) {
								Pair<Integer, Integer> pair = new Pair<Integer, Integer>(lineNumber, i + 1);
								result.add(pair);
							} 
							i = i + 2 * prefix.length() - 1;
							j = prefix.length() - 1;
						} else {
							i--;
							j--;
						}
					} else {
						char character = lineString.charAt(i);
						int lastOccurrence = occurrence.getOccurence(character);
						i = i + prefix.length() - Math.min(j, 1 + lastOccurrence);
						j = prefix.length() - 1;
					}
				}
			} 
		}
		return result;
		
	}
	
	
	/**
	 * Searches the document for lines that contain all the words in the 'words' parameter.
	 * Implements simple "and" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 * 
	 * Run time: O()
	 * 
	 * @param words Array of words to find on a single line in the document.
	 * @return List of line numbers on which all the words appear in the document.
	 *         Returns an empty list if the words do not appear in any line in the document.
	 * @throws IllegalArgumentException if words is null or an empty array 
	 *                                  or any of the Strings in the array are null or empty.
	 */
	@Override
	public List<Integer> wordsOnLine(String[] words) throws IllegalArgumentException {
		List<Integer> result = new ArrayList<Integer>();
		int size = words.length;
		if (size == 0) {
			throw new IllegalArgumentException();
		}
		MapIterator iterator = stringTable.getIterator();
		while (iterator.hasNext()) {
			MapNode lineNode = iterator.next();
			int lineNumber = lineNode.getLineNumber();
			String lineString = lineNode.getLineContent();
			for (int i = 0; i < size; i++) {
				String currentWord = words[i];
				if (boyerMoore(lineString, currentWord) != -1) {
					if (i == size - 1) {
						result.add(lineNumber);
					}
				} else {
					break;
				}
			}
		}
		return result;
		
			
	}

	@Override
	public List<Integer> someWordsOnLine(String[] words) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.someWordsOnLine(words);
	}

	@Override
	public List<Integer> wordsNotOnLine(String[] wordsRequired, String[] wordsExcluded)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.wordsNotOnLine(wordsRequired, wordsExcluded);
	}

	@Override
	public List<Triple<Integer, Integer, String>> simpleAndSearch(String[] titles, String[] words)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.simpleAndSearch(titles, words);
	}

	@Override
	public List<Triple<Integer, Integer, String>> simpleOrSearch(String[] titles, String[] words)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.simpleOrSearch(titles, words);
	}

	@Override
	public List<Triple<Integer, Integer, String>> simpleNotSearch(String[] titles, String[] wordsRequired,
			String[] wordsExcluded) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.simpleNotSearch(titles, wordsRequired, wordsExcluded);
	}

	@Override
	public List<Triple<Integer, Integer, String>> compoundAndOrSearch(String[] titles, String[] wordsRequired,
			String[] orWords) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.compoundAndOrSearch(titles, wordsRequired, orWords);
	}
	
	private int boyerMoore(String text, String pattern) {
		int i = pattern.length() - 1;
		int j = pattern.length() - 1;
		OccurrenceTable occurrence = new OccurrenceTable(pattern);
		while (i < text.length() - 1) {
			if (Character.toLowerCase(text.charAt(i)) == Character.toLowerCase(pattern.charAt(j))) { //if matches
				if (j == 0) {
					if (i > 0 && !Character.isAlphabetic(text.charAt(i - 1)) && !Character.isAlphabetic(text.charAt(i + pattern.length()))) { //end of word seperated by space
						return i + 1;
					} else if (!Character.isAlphabetic(text.charAt(i + pattern.length()))) {
						return i;
					}
					i = i + 2 * pattern.length() - 1;
					j = pattern.length() - 1;
				} else {
					i--;
					j--;
				}
			} else {
				char character = text.charAt(i);
				int lastOccurrence = occurrence.getOccurence(character);
				i = i + pattern.length() - Math.min(j, 1 + lastOccurrence);
				j = pattern.length() - 1;
			}
		}
		return -1;
	}
}
