package decompress.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import business.HuffmanBTree;
import utils.*;

/**
 * Use: java Decompress inputCompressedFile [outputFile]
 * @author Tanguy, Alexandre, mathieu, Jonathan
 *
 */
public class Decompress {
	String inputFile;
    String outputFile;
    String defaultFile = "resultFile.txt";
    int nbrCharToRead = 0;
	private HuffmanBTree huffmanBTree;
	BufferedWriter bw;
	InputBitStream in;
	
	/**
	 * Point d'entrï¿½e du programme Decompress
	 * @param args
	 */
	public static void main(String[] args){
		Decompress decompress = new Decompress();
		decompress.start(args);
	}

	/**
	 * Constructeur par dï¿½faut
	 */
	public Decompress(){
		
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, ï¿½criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.init();
		this.nbrCharToRead = this.readNbrCharCompressed();
		this.huffmanBTree = this.readHuffmanBTree();
		this.decompressFile();
		this.closeStream();
	}
	
	private void init() {
		try {
			this.in = new InputBitStream(inputFile);
			FileWriter fw = new FileWriter (this.outputFile);
			this.bw = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeStream(){
		try {
			this.in.close();
			this.bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * @pre --
     * @post extrait le fichier d'entrï¿½e et le fichier de sortie du tableau d'arguments args
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
	 * @pre inputFile pointe vers un fichier compressï¿½
	 * @post lit la premiï¿½re information contenue dans le fichier compressï¿½, ï¿½ savoir le nombre de caractï¿½res qui ont ï¿½tï¿½ compressï¿½s et ï¿½crit dedans.
	 * Retourne cette valeur.
	 */
	private int readNbrCharCompressed() {
		int nb = 0;
		try {
			nb = in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nb;
	}
	
	/**
	 * @pre inputFile pointe vers un fichier compressï¿½
	 * @post Lit et interprete la deuxiï¿½me information contenue dans le fichier compressï¿½, ï¿½ savoir l'arbre de Huffman.
	 * 		Cet arbre est ecnodï¿½ en binaire.
	 * 		Un bit de valeur 0 signifie qu'on doit ajouter un noeud interne ï¿½ l'arbre + 2 noeuds enfants et qu'on se dï¿½place vers le fils de gauche
	 * 		Un but ï¿½ 1 signifie qu'on lit une feuille et sera suivi de la reprï¿½sentation (CHAR) de cette feuille
	 * 		On est capable de dï¿½duire quand on a lu l'arbre en entier ï¿½ partir du moment oï¿½ l'on a lu le dernier fils droit du dernier noeud interne.
	 * 		
	 */
	private HuffmanBTree readHuffmanBTree() {
		boolean bit = false;
		HuffmanBTree result;
		try {
			 bit = in.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(bit == false) {
			HuffmanBTree gauche = readHuffmanBTree();
			HuffmanBTree droite = readHuffmanBTree();
			result = new HuffmanBTree(0, gauche, droite); //on ne tient pas compte de la fréquence ici
		} else {
			char c='0';
			try {
				c = in.readChar();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(c);
			result = new HuffmanBTree(c, 1);
		}
		// TODO read bit by bit
		//if 1 then read Char
		return result;
	}

    /**
     * @pre inputFile pointe vers un fichier compressï¿½
     * 		outpuFile est un chemin valide vers le fichier de sortie qui contiendra la version dï¿½compressï¿½e.
     * 		nbrCharToRead indique le nombre de caractï¿½re qui devront ï¿½tre lus
     * 		huffmanBTree est un arbre Huffman permettant de dï¿½coder correctement le fichier
     * @post Le reste du fichier inputFile est lu bit par bit.
     * 		Pour chaque bit, on parcourt l'arbre de Huffman.
     * 			Si on lit un bit ï¿½ 0, on descend vers le fils gauche.
     * 			Si on lit un bit ï¿½ 1, on descend vers le fils droit.
     * 			On s'arrete lorsqu'on arrive ï¿½ une feuille.
     * 			On ï¿½crit le caractï¿½re correspondant dans le fichier outputFile.
     * 		On rï¿½pï¿½te l'opï¿½ration jusqu'ï¿½ avoir lu nbrCharToRead caractï¿½res.
     */
	private void decompressFile(){
		HuffmanBTree tmp = this.huffmanBTree;
		while(this.nbrCharToRead > 0) {
			if(tmp.isLeaf()) {
				char c = tmp.getChar();
				try {
					System.out.print(c);
					this.bw.write(c);
				} catch (IOException e) {
					e.printStackTrace();
				}
				tmp = this.huffmanBTree;
				nbrCharToRead--;
			} else {
				boolean bit = false;
				try {
					bit = this.in.readBoolean();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(bit == false) {
					tmp = tmp.getLeft();
				} else {
					tmp = tmp.getRight();
				}
			}
		}
	}
}
