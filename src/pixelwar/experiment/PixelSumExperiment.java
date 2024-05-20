package pixelwar.experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;
import pixelwar.tree.ImageTree;


public class PixelSumExperiment {
	private static final Object mutex = new Object();
	 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTree img1 = null;
	 		ImageTree img2 = null;
	 		ImageTree img3 = null;

			ExecutorService pool1 = null;
			ExecutorService pool2 = null;
			ExecutorService pool3 = null;
			
			/* Métrique des expérimentations : variable qui compte le nombre de tuiles posées par tous les threads*/
			AtomicInteger cptGlobal1 = null; 
			AtomicInteger cptGlobal2 = null; 
			AtomicInteger cptGlobal3 = null; 
			
			/*Valeurs maximum des métriques à changer selon les caractéristiques matérielles*/
			int maxToile = 512; // max 512 pour 16Go RAM, max 4096 pour 32Go RAM
			int maxThreads = 1000; // si durée d'execution trop longue, réduire nombre de threads
			
			/* Paramètres des expérimentations */
			int tailleTuile; // taille du côté de la tuile
		    int nbThreads; // nombre de threads du pool
		    int duration; // durée de l'expérience en millisecondes
		    int tailleToile; // taille du côté du canvas
		    
		    
		    /* fichier de sortie */
		    String resultPath;
		    
		    /* autres variables */
		    Thread t1, t2, t3;
		    
		    
		    
		    
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			// Paramètres fixes 
		    tailleTuile = 6;
		    nbThreads = 20;
		    duration = 4000;
		    
		    resultPath = "data/pixelSum/testImgSize_sum.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleToile = 8; tailleToile <= maxToile; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleToile);
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        	
		        	cptGlobal1 = new AtomicInteger();
		        	cptGlobal2 = new AtomicInteger();
		        	cptGlobal3 = new AtomicInteger();
		        	
		        	// variables pour le code des threads juste en dessous qui a besoin de variables final
		        	final ExecutorService poolbis1 = pool1;
		        	final ExecutorService poolbis2 = pool2;
		        	final ExecutorService poolbis3 = pool3;
		        	final int durationbis = duration;
		        	
		        	// Démarrer des threads qui arrêteront les pools après un certain délai
		        	t1 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis1.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis1.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t1.start();
		        	
		        	// chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() 
			    	for(int j = 0; (j < nbThreads) && (!pool1.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool1.submit(new DrawTilePixelSum(img1, tailleTuile, cptGlobal1));
			    		}
			    	}
			    	t1.join();
		        	
		        	t2 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis2.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis2.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t2.start();
		        	
		        	for(int j = 0; (j < nbThreads) && (!pool2.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool2.submit(new DrawTilePixelSum(img2, tailleTuile, cptGlobal2));
			    		}
			    	}
		        	t2.join();
		        	
		        	t3 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis3.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis3.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t3.start();
			    	
			    	for(int j = 0; (j < nbThreads) && (!pool3.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool3.submit(new DrawTilePixelSum(img3, tailleTuile, cptGlobal3));
			    		}
			    	}
			    	t3.join();
			    	
			    	out.write(tailleToile + " " + cptGlobal1.get() + " " + cptGlobal2.get() + " " + cptGlobal3.get() + "\n");
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    
		    
	    	
	    	
	    	
	    	
	    	// on fait varier la taille des tuiles
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier la taille de la tuile");
			
	    	// Paramètres fixes
	    	tailleToile = 1024; // max 1024 pour 32 Go, max 
		    nbThreads = 20;
		    duration = 4000;
		    
		    resultPath = "data/pixelSum/testTileSize_sum.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleTuile = 2; tailleTuile < tailleToile; tailleTuile *= 2) {
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleToile);
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        	
		        	cptGlobal1 = new AtomicInteger();
		        	cptGlobal2 = new AtomicInteger();
		        	cptGlobal3 = new AtomicInteger();
		        	
		        	// variables pour le code des threads juste en dessous qui a besoin de variables final
		        	final ExecutorService poolbis1 = pool1;
		        	final ExecutorService poolbis2 = pool2;
		        	final ExecutorService poolbis3 = pool3;
		        	final int durationbis = duration;
		        	
		        	// Démarrer des threads qui arrêteront les pools après un certain délai 
		        	t1 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis1.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis1.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t1.start();
		        	
		        	// chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown()
			    	for(int j = 0; (j < nbThreads) && (!pool1.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool1.submit(new DrawTilePixelSum(img1, tailleTuile, cptGlobal1));
			    		}
			    	}
			    	t1.join();
		        	
		        	t2 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis2.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis2.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t2.start();
		        	
		        	for(int j = 0; (j < nbThreads) && (!pool2.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool2.submit(new DrawTilePixelSum(img2, tailleTuile, cptGlobal2));
			    		}
			    	}
		        	t2.join();
		        	
		        	t3 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis3.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis3.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t3.start();
			    	
			    	for(int j = 0; (j < nbThreads) && (!pool3.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool3.submit(new DrawTilePixelSum(img3, tailleTuile, cptGlobal3));
			    		}
			    	}
			    	t3.join();
			    	
			    	out.write(tailleTuile + " " + cptGlobal1.get() + " " + cptGlobal2.get() + " " + cptGlobal3.get() + "\n");
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
	    	
	    	
	    	
	    	
	    	
	    	// on fait varier le nombre de threads
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier le nombre de threads");
			
	    	/* Paramètres fixes */
	    	tailleToile = 1024;
	    	tailleTuile = 16;
		    duration = 4000;
		    
		    resultPath = "data/pixelSum/testNbThreads_sum.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (nbThreads = 1; nbThreads <= 150; nbThreads  += 20) {
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleToile);
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        	
		        	cptGlobal1 = new AtomicInteger();
		        	cptGlobal2 = new AtomicInteger();
		        	cptGlobal3 = new AtomicInteger();
		        	
		        	/* variables pour le code des threads juste en dessous qui a besoin de variables final */
		        	final ExecutorService poolbis1 = pool1;
		        	final ExecutorService poolbis2 = pool2;
		        	final ExecutorService poolbis3 = pool3;
		        	final int durationbis = duration;
		        	
		        	/* Démarrer des threads qui arrêteront les pools après un certain délai */ 
		        	t1 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis1.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis1.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t1.start();
		        	
		        	/* chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() */
			    	for(int j = 0; (j < nbThreads) && (!pool1.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool1.submit(new DrawTilePixelSum(img1, tailleTuile, cptGlobal1));
			    		}
			    	}
			    	t1.join();
		        	
		        	t2 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis2.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis2.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t2.start();
		        	
		        	for(int j = 0; (j < nbThreads) && (!pool2.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool2.submit(new DrawTilePixelSum(img2, tailleTuile, cptGlobal2));
			    		}
			    	}
		        	t2.join();
		        	
		        	t3 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis3.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis3.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t3.start();
			    	
			    	for(int j = 0; (j < nbThreads) && (!pool3.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool3.submit(new DrawTilePixelSum(img3, tailleTuile, cptGlobal3));
			    		}
			    	}
			    	t3.join();
			    	
			    	out.write(nbThreads + " " + cptGlobal1.get() + " " + cptGlobal2.get() + " " + cptGlobal3.get() + "\n");
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    	
	    	
	    	
	    	
	    	
	    	// on fait varier la durée de l'expériementation
	    	System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    	System.out.println("On fait varier la durée de l'expérimentation");
			
	    	/* Paramètres fixes */
	    	tailleToile = 32;
	    	nbThreads = 5;
	    	tailleTuile = 3;
		    
	    	resultPath = "data/pixelSum/testDuration_sum.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (duration = 100; duration <= 10000; duration = duration*2) { // on double la durée à chaque fois
		    		System.out.println("duration = " + duration);
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleToile);
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        	
		        	cptGlobal1 = new AtomicInteger();
		        	cptGlobal2 = new AtomicInteger();
		        	cptGlobal3 = new AtomicInteger();
		        	
		        	/* variables pour le code des threads juste en dessous qui a besoin de variables final */
		        	final ExecutorService poolbis1 = pool1;
		        	final ExecutorService poolbis2 = pool2;
		        	final ExecutorService poolbis3 = pool3;
		        	final int durationbis = duration;
		        	//System.out.println("duration = " + duration);
		        	/* Démarrer des threads qui arrêteront les pools après un certain délai */ 
		        	t1 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis1.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis1.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t1.start();
		        	System.out.println("duration = " + duration);
		        	/* chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() */
			    	for(int j = 0; (j < nbThreads) && (!pool1.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool1.submit(new DrawTilePixelSum(img1, tailleTuile, cptGlobal1));
			    		}
			    	}
			    	t1.join();
			    	System.out.println("duration = " + duration);
		        	t2 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis2.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis2.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t2.start();
		        	
		        	for(int j = 0; (j < nbThreads) && (!pool2.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool2.submit(new DrawTilePixelSum(img2, tailleTuile, cptGlobal2));
			    		}
			    	}
		        	t2.join();
		        	
		        	t3 = new Thread(() -> {
		        		try {
							Thread.sleep(durationbis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        		synchronized(mutex) {
							poolbis3.shutdownNow(); // arrêter les threads du pool même s'ils n'ont pas terminé
						    try {
								poolbis3.awaitTermination(0, TimeUnit.SECONDS);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
		        	});
		        	t3.start();
			    	
			    	for(int j = 0; (j < nbThreads) && (!pool3.isShutdown()); j++) {
			    		synchronized(mutex) {
			    			pool3.submit(new DrawTilePixelSum(img3, tailleTuile, cptGlobal3));
			    		}
			    	}
			    	t3.join();
			    	
			    	out.write(duration + " " + cptGlobal1.get() + " " + cptGlobal2.get() + " " + cptGlobal3.get() + "\n");
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
	    	
	}
		
}
