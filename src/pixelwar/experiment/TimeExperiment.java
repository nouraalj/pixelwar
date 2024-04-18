package pixelwar.experiment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.DrawTile;
import pixelwar.Tile;
import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;
import pixelwar.tree.ImageTree;

public class TimeExperiment {
	 private static int maxSize = 2<<4;
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
			// selon la taille de l'image et le nombre de threads : 
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taiile de l'arbre");
			
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
	 	
	 	
	 	
	 	/*
		// boucle pour le nombre de threads
	    for (int j = 1; j <= 20; j++) {
	         // boucle pour les tailles d'image
	         for (int i = 2; i <= maxSize; i <<= 1) {
	        	 img = new ImageTreeMutex(i);
	        	 pool = Executors.newFixedThreadPool(j);
	        	 Future<Long> result = pool.submit(new DrawTileTime(img,2));
	        	 System.out.println("Résultat pour une taille d'image : " + i + " et " + j + " threads : " + result.get());
	        	 System.out.println();

	             pool.shutdown();
	             pool.awaitTermination(15, TimeUnit.SECONDS);
	                
	         }
	    }
	    */
		
	 
	/* test de stratégie 2 avec ImageTreePixelMutex : 
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			ImageTreePixelMutex img = null;
			ExecutorService pool = null;
			// selon la taille de l'image et le nombre de threads : 
			int i = 2;
			while(i < maxSize) {
				for(int j = 1; j<20 ; j++) {
					img = new ImageTreePixelMutex(i);
					pool = Executors.newFixedThreadPool(i);
				
					Future<Long> result = (Future<Long>) pool.submit(new DrawTileTime(new Tile(img, 2), img)); // optimiser le passage des arguments, là on passe 2 fois l'arbre
					System.out.println("Résultat pour une taille d'image : "+ i + " et "+ j + " threads : " + result.get());
				}
				i = i<<1;
			}
			pool.shutdown();
			pool.awaitTermination(15, TimeUnit.SECONDS); // attend que tous les threads aient terminé
			
			//String path = "test_strat2.txt";
			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
			//img.exportImage(path);
		}
	*/
}
