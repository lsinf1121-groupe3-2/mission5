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
	 * Point d'entrée du programme Compress
	 * @param args
	 */
	public static void main(String[] args){
		Compress compress = new Compress();
		compress.start(args);
	}

	/**
	 * Controleur par défaut
	 */
	public Compress(){
		charFrequency = new HashMap<>();
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, écriture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.readFileAndCountCharFrequency(inputFile);
		this.createHuffmanTree(charFrequency);
		this.compressFile(inputFile, outputFile);
	}

	/**
     * @pre --
     * @post extrait le fichier d'entrée et le fichier de sortie du tableau d'arguments args
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
	 * @pre charFrequency map est initialisé
	 * 		inputFile pointe vers un fichier texte
	 * @post pour chaque caractère du fichier d'entrée, on a compter le nombre d'occurences.
	 * 		cette information est stockée dans la HashMap charFrequency qui associe un caractère à sa fréquence.
	 */
	private void readFileAndCountCharFrequency(String inputFile) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @pre charFrequency est une Map<Character, Integer> contenant une liste de caratère associé à une fréquence.
	 * @post On crée un arbre de Huffman pour chaque entrée dans la Map
	 * 		On les ajoute à la file de priorité priorityQueue
	 * 		On merge tous les arbres en un seul Huffman Tree
	 * 		(On a un mapping entre chaque caractère et sa représentation en bit) [???]
	 */
	private void createHuffmanTree(Map<Character, Integer> charFrequency2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @pre huffmanBTree est un arbre de Huffman complet, associant chaque caractère à un code binaire.
	 * @post On écrit la version binaire du Huffman Tree dans le fichier outputFIle
	 * 		On lit le fichier inputFile char par char.
	 * 		Pour chaque char, on écrit la version binaire correspondante
	 * 		On compte le nombre de bits écrits
	 * 		On écrit le nombre de bits écrit dans le fichier (type Integer). Cette information utile lors de la décompression pour connaitre le nombre de bits à lire.
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
