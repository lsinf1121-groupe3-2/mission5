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
	
	public HuffmanBTree(){
		;
	}
	
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
	
	//TODO copié des parties de code d'internet sans être sur du tout!
	public void addCharToBits(BitSet bits, HuffmanBTree current,int bitIndex){
		char c = current.getChar();
		int intC = (int)c;
		boolean[] bool = new boolean[7];
		for (int i = 15; i >= 0; i--) {
	        bool[i] = (intC & (1 << i)) != 0;
	    }
		for (int i = 0;i<16;i++) {
			bits.set(bitIndex+i,bool[i]);
		}
	}
	
	public BitSet compressTree(){
		BitSet bits = new BitSet();
		int bitIndex = 0;
		HuffmanBTree current;
		
		Stack<HuffmanBTree> toCompress = new Stack<HuffmanBTree>();
		toCompress.push(this);
		
		while(!toCompress.isEmpty()){
			current = toCompress.pop();
			if(current.isLeaf()){
				bits.set(bitIndex,true);
				addCharToBits(bits,current,bitIndex);
				bitIndex+=17;
			} else {
				bits.set(bitIndex,false);
				toCompress.push(current.getRight());
				toCompress.push(current.getLeft());
				bitIndex+=1;
			}
		}
		return bits;
	}
	
	//Pour que la file de priorité ordonne les arbres par ordre de fréquence
	public int compareTo(HuffmanBTree other) {
		return this.getFreq() - other.getFreq();
	}
	
	//Pour trouver si un arbre a un certain caractère
	public boolean characterIs(char c){
		return this.character == c;
	}
	
	//get methods
	public HuffmanBTree getLeft() {return left;}
	public HuffmanBTree getRight() {return right;}
	public void setLeft(HuffmanBTree t) {this.left = t;}
	public void setRight(HuffmanBTree t) {this.right = t;}
	public int getFreq() {return freq;}
	public char getChar(){return character;}
	public boolean isLeaf() {return isLeaf;}
	public void incrementFreq() {this.freq+=1;}
	public boolean hasLeftChild() {return this.left != null;}
}
