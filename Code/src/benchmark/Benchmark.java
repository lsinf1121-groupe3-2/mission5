package benchmark;

//import Compress.java;
/**
 * Use: Benchmark of Compress and Decompress
 * @author Jonathan
 *
 */

public class Benchmark {
	
	/** Prend en arguments n fichier a compresser */
	public static void main(String[] args) {
		//Compress compressed;
		int numberOfFile = args.length;
		int taux;
		int i;
		for (i = 0; i < numberOfFile; i++) {
			
			long t1 = System.nanoTime();
			//compressed = new (args[1], args[1]+".huffman" );
			long t2 = System.nanoTime();
			
			System.out.println("Execution time: " + ((t2 - t1) * 1e-6) + " milliseconds");
		
		
		}
		
	}
	
}
