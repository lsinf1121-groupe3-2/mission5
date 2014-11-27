package compress.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import business.HuffmanBTree;
import business.exception.EmptyQueueException;
import utils.*;

public class Compress {
    String inputFile;
    String outputFile;
    String defaultFile = "compressed.gr32";
    Map<Character, Integer> charFrequency;
    PriorityQueue<HuffmanBTree> priorityQueue;
    HuffmanBTree huffmanBTree;
    BufferedReader br;
    Map<Character, Vector<Boolean>> charBitCode;
	
	/**
	 * Point d'entr�e du programme Compress
	 * @param args
	 */
	public static void main(String[] args){
		Compress compress = new Compress();
		compress.start(args);
	}

	/**
	 * Controleur par d�faut
	 */
	public Compress(){
		charFrequency = new HashMap<>();
		priorityQueue = new PriorityQueue<>();
		huffmanBTree = new HuffmanBTree();
		charBitCode = new HashMap<>();
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, �criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.readFileAndCountCharFrequency(inputFile);
		this.createHuffmanTree(charFrequency);
		this.generateCharBitCode(huffmanBTree, null);
		this.compressFile(inputFile, outputFile);
	}

	/**
     * @pre --
     * @post extrait le fichier d'entr�e et le fichier de sortie du tableau d'arguments args
     */
    private void parseArgs(String[] args){
		if (args.length > 0 && args.length <= 2 && args[0] != null && !args[0].isEmpty()) { 
    		this.inputFile = args[0];
    		
    		if(args.length > 1 && args[1] != null && !args[1].isEmpty() ) {
    			this.outputFile = args[1];
    		}else{
    			this.outputFile = defaultFile;
    		}
    	}
		else{
			System.out.println("First argument must be a valid path to the input file");
			System.exit(-1);
		}
    }

    private void initializeReader(String inputFile){
		try {
			InputStream ips = new FileInputStream(inputFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			this.br = new BufferedReader(ipsr);
		} catch (FileNotFoundException e1) {
			System.out.println("Commands file not found. please check the path.");
			System.exit(-2);
		}
    }
    
    
	/**
	 * @pre charFrequency map est initialis�
	 * 		inputFile pointe vers un fichier texte
	 * @post pour chaque caract�re du fichier d'entr�e, on a compt� le nombre d'occurences.
	 * 		cette information est stock�e dans la HashMap charFrequency qui associe un caract�re � sa fr�quence.
	 */
	private void readFileAndCountCharFrequency(String inputFile) {
		this.initializeReader(inputFile);
		char charRead;
		int intRead;
		int newFreq;
		try {
			while ((intRead = br.read())!=-1){
				charRead = (char) intRead;
				if(charFrequency.containsKey(charRead)){
					newFreq = charFrequency.get(charRead) + 1;
					charFrequency.put(charRead, newFreq);
				} else {
					charFrequency.put(charRead,1);
				}
			}
		} catch (IOException e) {
			System.out.println("Error while I/O operations");
			System.exit(-5);
		}
	}
	
	/**
	 * @pre charFrequency est une Map<Character, Integer> contenant une liste de carat�re associ� � une fr�quence.
	 * @post On cr�e un arbre de Huffman pour chaque entr�e dans la Map
	 * 		On les ajoute � la file de priorit� priorityQueue
	 * 		On merge tous les arbres en un seul Huffman Tree
	 */
	private void createHuffmanTree(Map<Character, Integer> charFrequency) {
		for(Entry<Character, Integer> entry : charFrequency.entrySet()) {
		    Character character = entry.getKey();
		    Integer frequency = entry.getValue();
		    
		    HuffmanBTree newTree = new HuffmanBTree(character, frequency);
		    this.priorityQueue.add(newTree);
		}
		try {
			this.huffmanBTree = HuffmanBTree.mergeAll(priorityQueue);
		} catch (EmptyQueueException e) {
			System.err.println("Priority Queue cannot be empty");
			System.exit(-2);
		}
	}

	/**
	 * @pre huffmanBTree est un arbre de huffman contenant des caract�res associ�s � une fr�quence.
	 * @post charBitCode associe chaque caract�re de huffmanBTree � une liste de Boolean ordonn�, repr�sentant ce caract�re en code binaire;
	 * 		Cette m�thode est appel�e r�cursivement sur les arbres enfants gauches et droits tant qu'on n'atteint pas une feuille.
	 * 		Lorsqu'on l'appelle r�cusrivement sur le fils gauche, un '0' est ajout� � bitCodeRepresentation
	 * 		Lorsqu'on l'appelle r�cusrivement sur le fils droit, un '1' est ajout� � bitCodeRepresentation
	 * 		Lorsqu'on ateint une feuille, la s�quence de String bitCodeRepresentation est convertie en Vector<Boolean> et ajout�e � la map charBitCode
	 */
	private void generateCharBitCode(HuffmanBTree huffmanBTree, String bitCodeRepresentation) {
		if(bitCodeRepresentation == null){
			bitCodeRepresentation = "";
		}
		if(huffmanBTree != null){
			if(huffmanBTree.isLeaf()){
				Vector<Boolean> bitCode = new Vector<>();
				for (char c : bitCodeRepresentation.toCharArray()) {
					if(c == '0')
						bitCode.add(new Boolean(false));
					else
						bitCode.add(new Boolean(true));
				}
				this.charBitCode.put(huffmanBTree.getChar(), bitCode);
			}
			else{
				this.generateCharBitCode(huffmanBTree.getLeft(), bitCodeRepresentation + '0');
				this.generateCharBitCode(huffmanBTree.getRight(), bitCodeRepresentation + '1');
			}
		}
	}
	
	/**
	 * @pre huffmanBTree est un arbre de Huffman complet, associant chaque caract�re � un code binaire.
	 * @post On �crit la version binaire du Huffman Tree dans le fichier outputFIle
	 * 		On �crit le nombre de carct�res dans le fichier. Cette information utile lors de la d�compression pour savoir quand s'arr�ter.
	 * 		On lit le fichier inputFile char par char.
	 * 		Pour chaque char, on �crit la version binaire correspondante
	 * 		On compte le nombre de bits �crits
	 * 		
	 * 		
	 */
	public void compressFile(String inputFile, String outputFile){
//		try {
//			OutputBitStream out = new OutputBitStream(outputFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
