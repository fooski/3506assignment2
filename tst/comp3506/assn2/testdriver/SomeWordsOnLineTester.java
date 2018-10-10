package comp3506.assn2.testdriver;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import comp3506.assn2.application.AutoTester;
import comp3506.assn2.application.Search;
import comp3506.assn2.utils.Pair;

public class SomeWordsOnLineTester {
	private static Search searchApplication;
	
	public static void main(String[] args) {
		String [] searchTerm = {"boggler", "carlot"};
		try {
			searchApplication = new AutoTester("files\\shakespeare.txt", "files\\shakespeare-index.txt", "files\\stop-words.txt");
			List<Integer> result = searchApplication.someWordsOnLine(searchTerm);
			Iterator<Integer> iterator = result.iterator();
			while (iterator.hasNext()) {
				Integer value = iterator.next();
				System.out.println(value);
			}
			 
			 
		} catch (FileNotFoundException | IllegalArgumentException e) {
			System.out.println("Opening files failed!");
			e.printStackTrace();
		}
	}

}
