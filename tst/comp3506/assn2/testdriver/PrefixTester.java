package comp3506.assn2.testdriver;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import comp3506.assn2.application.AutoTester;
import comp3506.assn2.application.Search;
import comp3506.assn2.utils.Pair;

public class PrefixTester {
	private static Search searchApplication;
	
	public static void main(String[] args) {
		try {
			searchApplication = new AutoTester("files\\shakespeare.txt", "files\\shakespeare-index.txt", "files\\stop-words.txt");
			List<Pair<Integer, Integer>> result = searchApplication.phraseOccurrence("obscure");
			Iterator<Pair<Integer, Integer>> iterator = result.iterator();
			int i = 1;
			System.out.println(result.size());
			while (iterator.hasNext()) {
				Pair<Integer, Integer> value = iterator.next();
				System.out.printf("%d. line %d Col %d\n", i++, value.getLeftValue(), value.getRightValue());
			}
			 
			 
		} catch (FileNotFoundException | IllegalArgumentException e) {
			System.out.println("Opening files failed!");
			e.printStackTrace();
		}
	}

}
