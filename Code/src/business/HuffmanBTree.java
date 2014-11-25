package business;

import java.util.PriorityQueue;


public class HuffmanBTree implements Comparable<HuffmanBTree>{

	private char character;
	private HuffmanBTree left;
	private HuffmanBTree right;
	private int freq;
	private boolean isLeaf;
	
	
	public HuffmanBTree(char c, int f) {
		this.character = c;
		this.freq = f;
		this.isLeaf = true;
	}
	
	public HuffmanBTree(int f, boolean isLeaf,HuffmanBTree left,HuffmanBTree right) {
		this.freq = f;
		this.isLeaf = isLeaf;
		this.left = left;
		this.right = right;
	}

	public HuffmanBTree merge(HuffmanBTree other) {
		int f = this.getFreq() + other.getFreq();
		HuffmanBTree parent = new HuffmanBTree(f,false,this,other);
		return parent;
	}
	
	public HuffmanBTree mergeAll(PriorityQueue<HuffmanBTree> treeList) throws Exception {
		if (treeList.size()==1){
			return treeList.peek();
		} else if (treeList.size()<1){
			throw new Exception();
		} else {
			HuffmanBTree t1 = treeList.poll();
			HuffmanBTree t2 = treeList.poll();
			HuffmanBTree parent = t1.merge(t2);
			treeList.add(parent);
			return mergeAll(treeList);
		}
	}
	
	public int compareTo(HuffmanBTree other) {
		return this.getFreq() - other.getFreq();
	}
	
	
	public HuffmanBTree getLeft() {
		return left;
	}
	
	public HuffmanBTree getRight() {
		return right;
	}
	
	public int getFreq() {
		return freq;
	}
	
	public char getChar(){
		return character;
	}
}
