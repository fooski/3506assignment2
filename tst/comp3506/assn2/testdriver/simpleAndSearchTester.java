package comp3506.assn2.testdriver;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import comp3506.assn2.application.AutoTester;
import comp3506.assn2.application.Search;
import comp3506.assn2.utils.Pair;
import comp3506.assn2.utils.Triple;

public class simpleAndSearchTester {
	private static Search searchApplication;
	
	public static void main(String[] args) {
		String [] titles = {"CYMBELINE", "THE TRAGEDY OF HAMLET", "THE FIRST PART OF KING HENRY THE FOURTH", 
	            "THE SECOND PART OF KING HENRY THE SIXTH", "KING RICHARD THE SECOND", "VENUS AND ADONIS"};
		String [] requiredWords = {"obscure", "rusty"};
		try {
			searchApplication = new AutoTester("files\\shakespeare.txt", "files\\shakespeare-index.txt", "files\\stop-words.txt");
			List<Triple<Integer, Integer, String>> results = searchApplication.simpleAndSearch(titles, requiredWords);
			Iterator<Triple<Integer, Integer, String>> iterator = results.iterator();
			while(iterator.hasNext()) {
				Triple<Integer, Integer, String> triple = iterator.next();
				System.out.printf("line %d, Col %d, Word %s\n", triple.getLeftValue(), triple.getCentreValue(), triple.getRightValue());
			}
			 
			 
		} catch (FileNotFoundException | IllegalArgumentException e) {
			System.out.println("Opening files failed!");
			e.printStackTrace();
		}
	}

}
