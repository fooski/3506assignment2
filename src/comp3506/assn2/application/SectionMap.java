package comp3506.assn2.application;

import java.util.Iterator;

import comp3506.assn2.utils.Pair;
import comp3506.assn2.utils.Triple;


/**
 * An array based data structure that holds sections of the text from the index file.
 * 
 * Memory usage: O(n) n being the number of sections in the text
 * 
 * @author Leon Zheng
 *
 */
public class SectionMap  {
	int size = 0;
	SectionNode[] sectionMapArray;
	public SectionMap(int size) {
		sectionMapArray = new SectionNode[size];
	}
	
	
	/**
	 * Adds a section to the sectionMap data structure. The section spans [start, end]
	 * 
	 * Run time: O(1)
	 * 
	 * @param sectionName the name of the section
	 * @param start the line number where this section starts
	 * @param end the line number where this section finishes (inclusive)
	 */
	public void addSection(String sectionName, int start, int end) {
		sectionMapArray[size] = new SectionNode(sectionName, start, end);
		size++;
	}
	
	/**
	 * Returns the size of the current SectionMap data structure.
	 * 
	 * @return size the size of the data structure
	 */
	public int getSize() {
		return size;
	}
	
	
	/**
	 * Returns the start and finish of the designate section if it is found in the SectionMap. Otherwise
	 * return a Pair with -1 for both values.
	 * 
	 *  Run time: O(n) n being the number of sections in the text.
	 * @param sectionName
	 * @return
	 */
	public Pair<Integer, Integer> getSection(String sectionName) {
		int start = -1, end = -1;
		for (int i = 0; i < sectionMapArray.length; i++) {
			if (sectionMapArray[i].getSectionName().equalsIgnoreCase(sectionName)) {
				start = sectionMapArray[i].getTriple().getCentreValue();
				end = sectionMapArray[i].getTriple().getRightValue();
			}
		}
		return new Pair<Integer, Integer> (start, end);
	}
	
	public SectionIterator getIterator() {
		return new SectionIterator();
	}
	
	private class SectionIterator implements Iterator<Triple<String, Integer, Integer>>{
		int next = 0;
		@Override
		public boolean hasNext() {
			return (next <= size - 1);
		}

		@Override
		public Triple<String, Integer, Integer> next() {
			return sectionMapArray[next++].getTriple();
		}
		
	}
	
	
	private class SectionNode {
		private String sectionName;
		private int start;
		private int end;
		
		private SectionNode(String sectionName, int start, int end) {
			this.sectionName = sectionName;
			this.start = start;
			this.end = end;
		}
		
		String getSectionName() {
			return sectionName;
		}
		
		Triple<String, Integer, Integer> getTriple() {
			return new Triple<String, Integer, Integer> (sectionName, start, end);
		}
		
	}
	
	

}
