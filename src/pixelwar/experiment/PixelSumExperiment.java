package pixelwar.experiment;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.strategy.ImageTreeMutex;
import pixelwar.tree.ImageTree;

public class PixelSumExperiment {

	
	private static int maxSize = 2<<4;
	 
	 
	// test de stratégie 2 avec ImageTreeMutex : 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
			AtomicInteger cpt = null;
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
		    int tailleTuile = 2;
		    int nbThreads = 20;
		    pool = Executors.newFixedThreadPool(nbThreads);
		    
	    	for (int tailleArbre = 2; tailleArbre <= maxSize; tailleArbre <<= 1) { // on commence à 4
	        	img = new ImageTreeMutex(tailleArbre);
	        	cpt = new AtomicInteger();
	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		pool.submit(new DrawTilePixelSum(img, tailleTuile, cpt));
		    	}  
		    }
	    	
		    pool.shutdownNow();
		    pool.awaitTermination(5, TimeUnit.SECONDS);
			//String path = "test_strat2.txt";
			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
			//img.exportImage(path);
	}
		
}
