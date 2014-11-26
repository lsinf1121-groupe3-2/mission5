package business;

import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Stack;

/**
*
* @author Boris
*/
public class HuffmanBTree implements Comparable<HuffmanBTree>{

	private char character;
	private HuffmanBTree left;
	private HuffmanBTree right;
	private int freq;
	private boolean isLeaf;
	
	//Constructor. Utilisé lorsqu'on crée des noeuds externes
	public HuffmanBTree(char c, int f) {
		this.character = c;
		this.freq = f;
		this.isLeaf = true;
	}
	
	//Constructor. Utilisé lorsq'un crée des noeds internes
	public HuffmanBTree(int f,HuffmanBTree left,HuffmanBTree right) {
		this.freq = f;
		this.isLeaf = false;
		this.left = left;
		this.right = right;
	}

	public HuffmanBTree merge(HuffmanBTree other) {
		int f = this.getFreq() + other.getFreq();
		HuffmanBTree parent = new HuffmanBTree(f,this,other);
		return parent;
	}
	
	public static HuffmanBTree mergeAll(PriorityQueue<HuffmanBTree> treeList) throws Exception {
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
	
	//TODO écrire le charactère en binaire
	public void addCharToBits(BitSet bits, HuffmanBTree current,int bitIndex){
		char c = current.getChar();
		
		;
	}
	
	//TO DO: faut il faire un string?
	public String binaryString(){
		BitSet bits = new BitSet();
		int bitIndex = 0;
		Stack<HuffmanBTree> currentParents = new Stack<HuffmanBTree>();
		HuffmanBTree current;
		currentParents.push(this);
		while(!currentParents.isEmpty()){
			current = currentParents.pop();
			if(current.isLeaf()){
				bits.set(bitIndex,true);
				addCharToBits(bits,current,bitIndex);
				bitIndex+=9;
			} else {
				bits.set(bitIndex,false);
				currentParents.push(current.getRight());
				currentParents.push(current.getLeft());
			}
		}
		return bString;
	}
	
	public int compareTo(HuffmanBTree other) {
		return this.getFreq() - other.getFreq();
	}
	
	//get methods
	public HuffmanBTree getLeft() {return left;}
	public HuffmanBTree getRight() {return right;}
	public int getFreq() {return freq;}
	public char getChar(){return character;}
	public boolean isLeaf() {return isLeaf;}
}
