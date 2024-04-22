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
	 private static int maxSize = 2<<4;
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
		    int tailleTuile = 2;
		    int nbThreads = 20;
		    pool = Executors.newFixedThreadPool(nbThreads);
	    	for (int tailleArbre = 2; tailleArbre <= maxSize; tailleArbre <<= 1) { // on commence à 4
	        	img = new ImageTreeMutex(tailleArbre);
	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
		    	}  
		    }
		    pool.shutdown();
		    pool.awaitTermination(15, TimeUnit.SECONDS);
			//String path = "test_strat2.txt";
			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
			//img.exportImage(path);
		    
		    
		    
			// On fait varier la taille de la tuile
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taiile de la tuile");
			
		    int tailleArbre = 16;
		    nbThreads = 20;
		    pool = Executors.newFixedThreadPool(nbThreads);
	    	for (tailleTuile = 1; tailleTuile < tailleArbre; tailleTuile++) { 
	        	img = new ImageTreeMutex(tailleArbre);
	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
		    	}  
		    }
		    pool.shutdown();
		    pool.awaitTermination(15, TimeUnit.SECONDS);
			//String path = "test_strat2.txt";
			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
			//img.exportImage(path);
		    
		    
		    
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 			
 		    tailleArbre = 512;
 		    tailleTuile = 25;
 	    	for (nbThreads  = 1; nbThreads < 250; nbThreads++) { 
 	        	img = new ImageTreeMutex(tailleArbre);
 	        	pool = Executors.newFixedThreadPool(nbThreads);
 	        	
 		    	for(int j = 0; j < 5 ; j++) {
 		    		Future<Long> result = pool.submit(new DrawTileTime(img, tailleTuile));
 		    		System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
 		    	}  
 		    }
 		    pool.shutdown();
 		    pool.awaitTermination(15, TimeUnit.SECONDS);
 			//String path = "test_strat2.txt";
 			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
 			//img.exportImage(path);
		}
}
