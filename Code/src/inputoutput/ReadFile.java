package inputoutput;

import java.util.BitSet;
import java.util.Stack;

import business.HuffmanBTree;

public class ReadFile {

	public ReadFile() {
		// TODO Auto-generated constructor stub
	}
	
	//en cours...
	public HuffmanBTree readTree(BitSet bits) {
		
		boolean finished = false;
		int bitIndex = 0;
		HuffmanBTree bigParent = new HuffmanBTree();
		Stack<HuffmanBTree> treeStack = new Stack<HuffmanBTree>();
		treeStack.push(bigParent);
		HuffmanBTree current;
		while(!finished){
			current = treeStack.pop();
			if(bits.get(bitIndex)){
				if(current.hasLeftChild()){
					current.setRight(new HuffmanBTree())
				}
			}
		}
		
		
		return bigParent
	}

}
