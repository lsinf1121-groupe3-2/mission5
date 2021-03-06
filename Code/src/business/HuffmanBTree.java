package business;

import java.util.ArrayList;
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
	
	//Constructor. Utilis� lorsqu'on cr�e des noeuds externes
	public HuffmanBTree(char c, int f) {
		this.character = c;
		this.freq = f;
		this.isLeaf = true;
	}
	
	//Constructor. Utilis� lorsq'un cr�e des noeuds internes
	public HuffmanBTree(int f,HuffmanBTree left,HuffmanBTree right) {
		this.freq = f;
		this.isLeaf = false;
		this.setLeft(left);
		this.setRight(right);
	}

	public HuffmanBTree merge(HuffmanBTree other) {
		int f = this.getFreq() + other.getFreq();
		HuffmanBTree parent = new HuffmanBTree(f,this,other);
		this.setParent(parent);
		other.setParent(parent);
		return parent;
	}
	
	/**
	 * @pre treeList est une priority queue contenant une liste de huffmanBTree orodnn� par fr�quence.
	 * @post Tous les arbres pr�sents dans la queue ont �t� fusionn�s en un seul arbre de Huffman, retourn� comme r�sultat.
	 * @throws EmptyQueueException si une priority queue est vide.
	 */
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
	
	/**
	 * Converti l'arbre en format binaire
	 * @return
	 */
	public void toBitCode(ArrayList<Boolean> bitList, ArrayList<Character> charList){
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
				toCompress.push(current.getRight());
				toCompress.push(current.getLeft());
			}
		}
	}
	
	//Pour que la file de priorit� ordonne les arbres par ordre de fr�quence
	public int compareTo(HuffmanBTree other) {
		return this.getFreq() - other.getFreq();
	}
	
	//Pour trouver si un arbre a un certain caract�re
	public boolean characterIs(char c){
		return this.character == c;
	}
	
	//utility methods
	public HuffmanBTree getLeft() {return left;}
	public HuffmanBTree getRight() {return right;}
	public HuffmanBTree getParent() {return parent;}
	public void setLeft(HuffmanBTree t) {this.left = t; if(t!=null)t.setParent(this);}
	public void setRight(HuffmanBTree t) {this.right = t; if(t!=null)t.setParent(this);}
	public void setParent(HuffmanBTree t) {this.parent = t;}
	public int getFreq() {return freq;}
	public char getChar(){return character;}
	public boolean isLeaf() {return isLeaf;}
	public void incrementFreq() {this.freq+=1;}
	public boolean hasLeftChild() {return this.left != null;}

	public void setCharacter(char c) {
		this.character = c;
		this.isLeaf = true;
		this.setLeft(null);
		this.setRight(null);
	}
}
