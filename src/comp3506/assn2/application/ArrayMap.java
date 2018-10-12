package comp3506.assn2.application;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;



/**
 * A simple map data structure which is used to store the textfile line by line. 
 * 
 * Memory Usage: O(n*M) n being number of lines in the textfile, M is the length of each lines
 * 
 * @author Leon Zheng
 *
 */
public class ArrayMap {
	private MapNode[] stringTable;
	int size = 1; //number of lines / elements in array
	
	/**
	 * Constructor of the class ArrayMap
	 * 
	 * @param file
	 * @param lines
	 */
	public ArrayMap(String file, int lines) {
		stringTable = new MapNode[lines + 1];
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringTable[size] = new MapNode(size, line);
				size++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Returns the line with the corresponding number
	 * 
	 * Run time: O(1)
	 * 
	 * @param lineNumber
	 * @return MapNode which corresponds 
	 */
	public String getLine(int lineNumber) {
		return stringTable[lineNumber].getLineContent();
	}
	
	/**
	 * Returns the size of the current data structure.
	 * 
	 * Run time: O(1)
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns an instance of the class Iterator, which allows iteration to be done on the data structure.
	 * @return new MapIterator
	 */
	public MapIterator getIterator() {
		return new MapIterator();
	}
	
	public class MapNode {
		int lineNumber;
		String lineContent;
		private MapNode(int lineNumber, String lineContent) {
			this.lineNumber = lineNumber;
			this.lineContent = lineContent;
		}
		
		public int getLineNumber() {
			return lineNumber;
		}
		
		public String getLineContent() {
			return lineContent;
		}
		
	}
	
	
	/**
	 * @author Leon
	 *
	 */
	private class MapIterator implements Iterator<MapNode> {
		private int next = 1;
		

		@Override
		public boolean hasNext() {
			return (next <= size - 1);
			
		}

		@Override
		public MapNode next() {
			return stringTable[next++];
		}
		
	}
}

