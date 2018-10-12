package comp3506.assn2.testdriver;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import comp3506.assn2.application.AutoTester;
import comp3506.assn2.application.Search;
import comp3506.assn2.utils.Pair;

public class indexLoadingTester {
	private static AutoTester searchApplication;
	
	public static void main(String[] args) {
		try {
			searchApplication = new AutoTester("files\\shakespeare.txt", "files\\shakespeare-index.txt", "files\\stop-words.txt");
			searchApplication.printSection();
			 
			 
		} catch (FileNotFoundException | IllegalArgumentException e) {
			System.out.println("Opening files failed!");
			e.printStackTrace();
		}
	}

}
