package decompress.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	OutputBitStream out;
	InputBitStream in;
	
	/**
	 * Point d'entr�e du programme Decompress
	 * @param args
	 */
	public static void main(String[] args){
		Decompress decompress = new Decompress();
		decompress.start(args);
	}

	/**
	 * Constructeur par d�faut
	 */
	public Decompress(){
		
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, �criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		//TODO: ouvrir et initiliaser les flux d'entr�es et de sorties (InputBitStream et OutputBitStream)
		this.init();
		this.nbrCharToRead = this.readNbrCharCompressed();
		this.huffmanBTree = this.readHuffmanBTree();
		this.decompressFile();
		//TODO: fermer les flux
	}
	
	private void init() {
		try {
			this.in = new InputBitStream(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @pre inputFile pointe vers un fichier compress�
	 * @post lit la premi�re information contenue dans le fichier compress�, � savoir le nombre de caract�res qui ont �t� compress�s et �crit dedans.
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
	 * @pre inputFile pointe vers un fichier compress�
	 * @post Lit et interprete la deuxi�me information contenue dans le fichier compress�, � savoir l'arbre de Huffman.
	 * 		Cet arbre est ecnod� en binaire.
	 * 		Un bit de valeur 0 signifie qu'on doit ajouter un noeud interne � l'arbre + 2 noeuds enfants et qu'on se d�place vers le fils de gauche
	 * 		Un but � 1 signifie qu'on lit une feuille et sera suivi de la repr�sentation (CHAR) de cette feuille
	 * 		On est capable de d�duire quand on a lu l'arbre en entier � partir du moment o� l'on a lu le dernier fils droit du dernier noeud interne.
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
			result = new HuffmanBTree(gauche.getFreq() + droite.getFreq(), gauche, droite);
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
     * @pre inputFile pointe vers un fichier compress�
     * 		outpuFile est un chemin valide vers le fichier de sortie qui contiendra la version d�compress�e.
     * 		nbrCharToRead indique le nombre de caract�re qui devront �tre lus
     * 		huffmanBTree est un arbre Huffman permettant de d�coder correctement le fichier
     * @post Le reste du fichier inputFile est lu bit par bit.
     * 		Pour chaque bit, on parcourt l'arbre de Huffman.
     * 			Si on lit un bit � 0, on descend vers le fils gauche.
     * 			Si on lit un bit � 1, on descend vers le fils droit.
     * 			On s'arrete lorsqu'on arrive � une feuille.
     * 			On �crit le caract�re correspondant dans le fichier outputFile.
     * 		On r�p�te l'op�ration jusqu'� avoir lu nbrCharToRead caract�res.
     */
	private void decompressFile(){
		try {
			this.out = new OutputBitStream(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HuffmanBTree tmp = this.huffmanBTree;
		while(this.nbrCharToRead > 0) {
			if(tmp.isLeaf()) {
				char c = tmp.getChar();
				try {
					//System.out.print(c);
					this.out.write(c);
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
