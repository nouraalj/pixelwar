package pixelwar.experiment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.drawing.DrawTile;
import pixelwar.drawing.Tile;
import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;
import pixelwar.tree.ImageTree;

public class TimeExperiment {
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
			
			/* Paramètres des expérimentations */
			int tailleTuile;
		    int nbThreads;
		    int tailleArbre;
			
			
		    
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 2;
		    nbThreads = 20;
		    
	    	for (tailleArbre = 2; tailleArbre <= 512; tailleArbre <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleArbre);
	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
		    	} 
		    	
		    	pool.shutdown();
			    pool.awaitTermination(15, TimeUnit.SECONDS);
			    
			    //récupérer le résultat ici
		    }
		    
	    	
	    	
	    	
		    
			// On fait varier la taille de la tuile
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taiile de la tuile");
			
			/* Paramètres fixes */
		    tailleArbre = 16;
		    nbThreads = 20;
		    
	    	for (tailleTuile = 1; tailleTuile < tailleArbre; tailleTuile++) {
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleArbre);
	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
		    	}
		    	
		    	pool.shutdown();
			    pool.awaitTermination(15, TimeUnit.SECONDS);
			    
			    //récupérer le résultat ici
		    }
		    
		    
		    
	    	
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 			
 			/* Paramètres fixes */
 		    tailleArbre = 32;
 		    tailleTuile = 10;
 		    
 	    	for (nbThreads  = 1; nbThreads <= 100; nbThreads++) { 
 	        	img = new ImageTreeMutex(tailleArbre);
 	        	pool = Executors.newFixedThreadPool(nbThreads);
 	        	
 		    	for(int j = 0; j < 5 ; j++) {
 		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
 		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
 		    	}
 		    	
 		    	pool.shutdown();
 	 		    pool.awaitTermination(15, TimeUnit.SECONDS);
 	 		    
 	 		    //récupérer le résultat ici
 		    }
		}
}
