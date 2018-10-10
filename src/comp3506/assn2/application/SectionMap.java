package comp3506.assn2.application;

import comp3506.assn2.utils.Triple;

public class SectionMap {
	int size;
	SectionNode[] sectionMapArray;
	public SectionMap(int size) {
		sectionMapArray = new SectionNode[size];
	}
	
	public void addSection(String sectionName, int start, int end) {
		sectionMapArray[size] = new SectionNode(sectionName, start, end);
		size++;
	}
	
	public int getSize() {
		return size;
	}
	
	public Triple<String, Integer, Integer> getSection(String sectionName) {
		for (int i = 0; i < sectionMapArray.length; i++) {
			if (sectionMapArray[i].getSectionName().equalsIgnoreCase(sectionName)) {
				return sectionMapArray[i].getTriple();
			}
		}
		return new Triple<String, Integer, Integer> ("", 0, 0);
	}
	
	
	private class SectionNode {
		private String sectionName;
		private int start;
		private int end;
		public SectionNode(String sectionName, int start, int end) {
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
