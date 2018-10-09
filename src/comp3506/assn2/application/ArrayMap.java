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
	 * Returns the corresponding node in the string table.
	 * @param lineNumber
	 * @return MapNode which corresponds 
	 */
	public MapNode getNode(int lineNumber) {
		return stringTable[lineNumber];
	}
	
	public int getSize() {
		return size;
	}
	
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
	
	class MapIterator implements Iterator<MapNode> {
		int next = 1;

		@Override
		public boolean hasNext() {
			if (next < size - 1) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public MapNode next() {
			return stringTable[next++];
		}
		
	}
}

