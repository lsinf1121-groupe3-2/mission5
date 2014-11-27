package business;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Stack;

import business.exception.EmptyQueueException;

/**
*
* @author Boris
*/
public class HuffmanBTree implements Comparable<HuffmanBTree>{

	private char character;
	private HuffmanBTree left;
	private HuffmanBTree right;
	private HuffmanBTree parent;
	private int freq;
	private boolean isLeaf;
	
	public HuffmanBTree(){
		
	}
	
	//Constructor. Utilisé lorsqu'on crée des noeuds externes
	public HuffmanBTree(char c, int f) {
		this.character = c;
		this.freq = f;
		this.isLeaf = true;
	}
	
	//Constructor. Utilisé lorsq'un crée des noeuds internes
	public HuffmanBTree(int f,HuffmanBTree left,HuffmanBTree right) {
		this.freq = f;
		this.isLeaf = false;
		this.left = left;
		this.right = right;
	}

	public HuffmanBTree merge(HuffmanBTree other) {
		int f = this.getFreq() + other.getFreq();
		HuffmanBTree parent = new HuffmanBTree(f,this,other);
		this.setParent(parent);
		other.setParent(parent);
		return parent;
	}
	
	public static HuffmanBTree mergeAll(PriorityQueue<HuffmanBTree> treeList) throws EmptyQueueException {
		if (treeList.size()==1){
			return treeList.peek();
		} else if (treeList.size()<1){
			throw new EmptyQueueException();
		} else {
			HuffmanBTree t1 = treeList.poll();
			HuffmanBTree t2 = treeList.poll();
			HuffmanBTree parent = t1.merge(t2);
			treeList.add(parent);
			return mergeAll(treeList);
		}
	}
	
	public void compressTree(ArrayList<Boolean> bitList, ArrayList<Character> charList){
		bitList.clear();
		charList.clear();
		
		Stack<HuffmanBTree> toCompress = new Stack<HuffmanBTree>();
		toCompress.push(this);
		
		HuffmanBTree current;
		while(!toCompress.isEmpty()){
			current = toCompress.pop();
			if(current.isLeaf()){
				bitList.add(new Boolean(true));
				charList.add(new Character(current.getChar()));
			} else {
				bitList.add(new Boolean(false));
			}
		}
	}
	
	//Pour que la file de priorité ordonne les arbres par ordre de fréquence
	public int compareTo(HuffmanBTree other) {
		return this.getFreq() - other.getFreq();
	}
	
	//Pour trouver si un arbre a un certain caractère
	public boolean characterIs(char c){
		return this.character == c;
	}
	
	//utility methods
	public HuffmanBTree getLeft() {return left;}
	public HuffmanBTree getRight() {return right;}
	public HuffmanBTree getParent() {return parent;}
	public void setLeft(HuffmanBTree t) {this.left = t;}
	public void setRight(HuffmanBTree t) {this.right = t;}
	public void setParent(HuffmanBTree t) {this.parent = t;}
	public int getFreq() {return freq;}
	public char getChar(){return character;}
	public boolean isLeaf() {return isLeaf;}
	public void incrementFreq() {this.freq+=1;}
	public boolean hasLeftChild() {return this.left != null;}
}
