package pixelwar.experiment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.strategy.ImageTreeMutex;
import pixelwar.tree.ImageTree;

public class PixelSumExperiment {
	private static final Object mutex = new Object();
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img = null;
			ExecutorService pool = null;
			
			/* Métrique des expérimentations */
			AtomicInteger cptGlobal = null; // variable qui compte le nombre de tuiles posées par tous les threads
			
			/* Paramètres des expérimentations */
			int tailleTuile; // taille du côté de la tuile
		    int nbThreads; // nombre de threads du pool
		    int duration; // durée de l'expérience en millisecondes
		    int tailleToile; // taille du côté du canvas
		    
		    
		    
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 2;
		    nbThreads = 20;
		    duration = 4000;
		    
	    	for (tailleToile = 2; tailleToile <= 2048; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleToile);
	        	cptGlobal = new AtomicInteger();
	        	
	        	/* 2 variables pour le code du thread juste en dessous qui a besoin de variables final */
	        	final ExecutorService poolbis = pool;
	        	final int durationbis = duration;
	        	
	        	/* Démarrer un thread qui arrêtera le pool après un certain délai */ 
	        	Thread t = new Thread(() -> {
	        		try {
						Thread.sleep(durationbis);
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
		    			pool.submit(new DrawTilePixelSum(img, tailleTuile, cptGlobal));
		    		}
		    	}
		    	
		    	t.join();
		    	// récupérer le réultat ici dans la variable cptGlobal
		    	System.out.println("nombre de tuiles posées : " + cptGlobal.get());
		    }
	    	
	    	
	    	
	    	
	    	// on fait varier la taille des tuiles
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier la taille de la tuile");
			
	    	/* Paramètres fixes */
	    	tailleToile = 32;
		    nbThreads = 20;
		    duration = 4000;
		    
	    	for (tailleTuile = 2; tailleTuile <= tailleToile; tailleTuile++) {
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleToile);
	        	cptGlobal = new AtomicInteger();
	        	
	        	/* 2 variables pour le code du thread juste en dessous qui a besoin de variables final */
	        	final ExecutorService poolbis = pool;
	        	final int durationbis = duration;
	        	
	        	/* Démarrer un thread qui arrêtera le pool après un certain délai */ 
	        	Thread t = new Thread(() -> {
	        		try {
						Thread.sleep(durationbis);
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
		    			pool.submit(new DrawTilePixelSum(img, tailleTuile, cptGlobal));
		    		}
		    	}
		    	
		    	t.join();
		    	// récupérer le réultat ici dans la variable cptGlobal
		    	System.out.println("nombre de tuiles posées : " + cptGlobal.get());
		    }
	    	
	    	
	    	
	    	
	    	
	    	// on fait varier le nombre de threads
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier le nombre de threads");
			
	    	/* Paramètres fixes */
	    	tailleToile = 32;
	    	tailleTuile = 2;
		    duration = 4000;
		    
	    	for (nbThreads = 1; nbThreads <= 30; nbThreads++) {
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleToile);
	        	cptGlobal = new AtomicInteger();
	        	
	        	/* 2 variables pour le code du thread juste en dessous qui a besoin de variables final */
	        	final ExecutorService poolbis = pool;
	        	final int durationbis = duration;
	        	
	        	/* Démarrer un thread qui arrêtera le pool après un certain délai */ 
	        	Thread t = new Thread(() -> {
	        		try {
						Thread.sleep(durationbis);
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
		    			pool.submit(new DrawTilePixelSum(img, tailleTuile, cptGlobal));
		    		}
		    	}
		    	
		    	t.join();
		    	// récupérer le réultat ici dans la variable cptGlobal
		    	System.out.println("nombre de tuiles posées : " + cptGlobal.get());
		    }
	    	
	    	
	    	
	    	
	    	
	    	// on fait varier la durée de l'expériementation
	    	System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier la durée de l'expériementation");
			
	    	/* Paramètres fixes */
	    	tailleToile = 32;
	    	nbThreads = 5;
	    	tailleTuile = 2;
		    
	    	for (duration = 10; duration <= 10000; duration = duration*2) { // on double la durée à chaque fois
	    		pool = Executors.newFixedThreadPool(nbThreads);
	        	img = new ImageTreeMutex(tailleToile);
	        	cptGlobal = new AtomicInteger();
	        	
	        	/* 2 variables pour le code du thread juste en dessous qui a besoin de variables final */
	        	final ExecutorService poolbis = pool;
	        	final int durationbis = duration;
	        	
	        	/* Démarrer un thread qui arrêtera le pool après un certain délai */ 
	        	Thread t = new Thread(() -> {
	        		try {
						Thread.sleep(durationbis);
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
		    			pool.submit(new DrawTilePixelSum(img, tailleTuile, cptGlobal));
		    		}
		    	}
		    	
		    	t.join();
		    	// récupérer le réultat ici dans la variable cptGlobal
		    	System.out.println("nombre de tuiles posées : " + cptGlobal.get());
		    }
	    	
	}
		
}
