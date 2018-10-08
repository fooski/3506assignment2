package comp3506.assn2.application;
import java.io.*;
import java.util.Iterator;


public class ArrayMap {
	private MapNode[] stringTable;
	int size = 0; //number of lines / elements in array
	
	public ArrayMap(String file, int lines) {
		stringTable = new MapNode[lines];
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
		int next = 0;

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
			return stringTable[next];
		}
		
	}
}

