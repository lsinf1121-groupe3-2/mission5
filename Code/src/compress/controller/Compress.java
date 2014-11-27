package compress.controller;

import java.io.*;
import utils.*;

import inputoutput.*;

public class Compress {
    String commandFile;
    String outputFile;
    String defaultFile = "resultFile.txt";
	
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
		
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, �criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.compressFile();
	}
	
	/**
     * @pre --
     * @post extrait le fichier d'entr�e et le fichier de sortie du tableau d'arguments args
     */
    private void parseArgs(String[] args){
		if (args.length > 0 && args.length <= 2 && args[0] != null && !args[0].isEmpty()) { 
    		this.commandFile = args[0];
    		
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
	
	public void compressFile(){
		OutputBitStream out = new OutputBitStream(filename);
	}
}
