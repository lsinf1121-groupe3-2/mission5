package compress.controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import business.HuffmanBTree;
import utils.*;
import inputoutput.*;

public class Compress {
    String inputFile;
    String outputFile;
    String defaultFile = "resultFile.txt";
    Map<Character, Integer> charFrequency;
    PriorityQueue<HuffmanBTree> priorityQueue;
    HuffmanBTree huffmanBTree;
	
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
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, �criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.readFileAndCountCharFrequency(inputFile);
		this.createHuffmanTree(charFrequency);
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

	/**
	 * @pre charFrequency map est initialis�
	 * 		inputFile pointe vers un fichier texte
	 * @post pour chaque caract�re du fichier d'entr�e, on a compter le nombre d'occurences.
	 * 		cette information est stock�e dans la HashMap charFrequency qui associe un caract�re � sa fr�quence.
	 */
	private void readFileAndCountCharFrequency(String inputFile) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @pre charFrequency est une Map<Character, Integer> contenant une liste de carat�re associ� � une fr�quence.
	 * @post On cr�e un arbre de Huffman pour chaque entr�e dans la Map
	 * 		On les ajoute � la file de priorit� priorityQueue
	 * 		On merge tous les arbres en un seul Huffman Tree
	 * 		(On a un mapping entre chaque caract�re et sa repr�sentation en bit) [???]
	 */
	private void createHuffmanTree(Map<Character, Integer> charFrequency2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @pre huffmanBTree est un arbre de Huffman complet, associant chaque caract�re � un code binaire.
	 * @post On �crit la version binaire du Huffman Tree dans le fichier outputFIle
	 * 		On lit le fichier inputFile char par char.
	 * 		Pour chaque char, on �crit la version binaire correspondante
	 * 		On compte le nombre de bits �crits
	 * 		On �crit le nombre de bits �crit dans le fichier (type Integer). Cette information utile lors de la d�compression pour connaitre le nombre de bits � lire.
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
