package decompress.controller;

import java.io.IOException;

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
	OutputBitStream out;
	InputBitStream in;
	
	/**
	 * Point d'entrée du programme Decompress
	 * @param args
	 */
	public static void main(String[] args){
		Decompress decompress = new Decompress();
		decompress.start(args);
	}

	/**
	 * Constructeur par défaut
	 */
	public Decompress(){
		
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, écriture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		//TODO: ouvrir et initiliaser les flux d'entrées et de sorties (InputBitStream et OutputBitStream)
		this.nbrCharToRead = this.readNbrCharCompressed();
		this.huffmanBTree = this.readHuffmanBTree();
		this.decompressFile();
		//TODO: fermer les flux
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
	 * @pre inputFile pointe vers un fichier compressé
	 * @post lit la première information contenue dans le fichier compressé, à savoir le nombre de caractères qui ont été compressés et écrit dedans.
	 * Retourne cette valeur.
	 */
	private int readNbrCharCompressed() {
		// TODO 
		// Utiliser InputBitStream.readInt()
		return 0;
	}
	
	/**
	 * @pre inputFile pointe vers un fichier compressé
	 * @post Lit et interprete la deuxième information contenue dans le fichier compressé, à savoir l'arbre de Huffman.
	 * 		Cet arbre est ecnodé en binaire.
	 * 		Un bit de valeur 0 signifie qu'on doit ajouter un noeud interne à l'arbre + 2 noeuds enfants et qu'on se déplace vers le fils de gauche
	 * 		Un but à 1 signifie qu'on lit une feuille et sera suivi de la représentation (CHAR) de cette feuille
	 * 		On est capable de déduire quand on a lu l'arbre en entier à partir du moment où l'on a lu le dernier fils droit du dernier noeud interne.
	 * 		
	 */
	private HuffmanBTree readHuffmanBTree() {
		// TODO read bit by bit
		//if 1 then read Char
		return null;
	}

    /**
     * @pre inputFile pointe vers un fichier compressé
     * 		outpuFile est un chemin valide vers le fichier de sortie qui contiendra la version décompressée.
     * 		nbrCharToRead indique le nombre de caractère qui devront être lus
     * 		huffmanBTree est un arbre Huffman permettant de décoder correctement le fichier
     * @post Le reste du fichier inputFile est lu bit par bit.
     * 		Pour chaque bit, on parcourt l'arbre de Huffman.
     * 			Si on lit un bit à 0, on descend vers le fils gauche.
     * 			Si on lit un bit à 1, on descend vers le fils droit.
     * 			On s'arrete lorsqu'on arrive à une feuille.
     * 			On écrit le caractère correspondant dans le fichier outputFile.
     * 		On répète l'opération jusqu'à avoir lu nbrCharToRead caractères.
     */
	private void decompressFile(){

	}
}
