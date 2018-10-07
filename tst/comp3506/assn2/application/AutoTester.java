package comp3506.assn2.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

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
	private OccurrenceTable occurrence;

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
	}

	@Override
	public int wordCount(String word) throws IllegalArgumentException {
		int count = 0;
		int length = word.length();
		int i = length - 1, j = length - 1;
		if (length == 0 || word.equals(null)) {
			throw new IllegalArgumentException();
		}
		occurrence = new OccurrenceTable(word);
		while (i < content.length() - 1) {
			if (content.charAt(i) == word.charAt(j)) { //if matches
				if (j == 0) {
					System.out.print(count);
					System.out.print(content.charAt(i));
					System.out.print(content.charAt(i+1));
					System.out.print(content.charAt(i+2));
					System.out.print(content.charAt(i+3));
					System.out.print(content.charAt(i+4));
					System.out.print(content.charAt(i+5));
					System.out.print(content.charAt(i+6));
					System.out.print(content.charAt(i+7));
					System.out.println(content.charAt(i+8));
					count++;
					i = i + 2 * length - 1;
					j = length - 1;
					//complete character jump
				} else {
					i--;
					j--;
				}
			} else {
				char character = content.charAt(i);
				//System.out.println(i);
				int lastOccurrence = occurrence.getOccurence(character);
				i = i + length - Math.min(j, 1 + lastOccurrence);
				j = length - 1;
			}
		}
		return count;
	}

	@Override
	public List<Pair<Integer, Integer>> phraseOccurrence(String phrase) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.phraseOccurrence(phrase);
	}

	@Override
	public List<Pair<Integer, Integer>> prefixOccurrence(String prefix) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.prefixOccurrence(prefix);
	}

	@Override
	public List<Integer> wordsOnLine(String[] words) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return Search.super.wordsOnLine(words);
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
}
