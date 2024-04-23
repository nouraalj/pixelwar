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
	private static final Object mutex = new Object();
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
			AtomicInteger cpt = null;
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
		    int tailleTuile = 2;
		    int nbThreads = 20;
		    int duration = 4000; // durée de l'expérience en millisecondes
		    
	    	for (int tailleArbre = 2; tailleArbre <= 2048; tailleArbre <<= 1) { // on commence à 4
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleArbre);
	        	cpt = new AtomicInteger();
	        	final ExecutorService poolbis = pool;
	        	
	        	/* Démarrer un thread qui arrêtera le pool après un certain délai */ 
	        	Thread t = new Thread(() -> {
	        		try {
						Thread.sleep(duration);
					} catch (InterruptedException e) {
						//e.printStackTrace();
					}
	        		synchronized(mutex) {
						poolbis.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
					    try {
							poolbis.awaitTermination(0, TimeUnit.SECONDS); // je sais pas s'il est utile là mais il fait pas de mal
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
	        	});
	        	t.start();
	        	
	        	/* chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() */
		    	for(int j = 0; (j < nbThreads) && (!pool.isShutdown()); j++) {
		    		synchronized(mutex) {
		    			pool.submit(new DrawTilePixelSum(img, tailleTuile, cpt));
		    		}
		    	}
		    	
		    	t.join();
		    	// récupérer le réultat ici dans la variable cpt
		    	System.out.println("nombre de tuiles posées : " + cpt.get());
		    }
			//String path = "test_strat2.txt";
			//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
			//img.exportImage(path);
	    	
	    	
	    	// on fait varier la taille des tuiles
	    	
	    	// on fait varier le nombre de threads
	    	
	    	// on fait varier la durée de l'expériementation
	    	
	}
		
}
