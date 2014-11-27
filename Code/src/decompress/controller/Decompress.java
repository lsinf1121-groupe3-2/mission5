package decompress.controller;

/**
 * Use: java Decompress inputCompressedFile [outputFile]
 * @author Tanguy, Alexandre, mathieu, Jonathan
 *
 */
public class Decompress {
	String commandFile;
    String outputFile;
    String defaultFile = "resultFile.txt";
	
	/**
	 * Point d'entr�e du programme Decompress
	 * @param args
	 */
	public static void main(String[] args){
		Decompress decompress = new Decompress();
		decompress.start(args);
	}

	/**
	 * Controleur par d�faut
	 */
	public Decompress(){
		
	}
	
	/**
	 * Lance le programme de compression.
	 * Contient toute la logique de lecture, compression, �criture
	 */
	private void start(String[] args) {
		this.parseArgs(args);
		this.decompressFile();
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
	
	public void decompressFile(){
		
	}
}
