package pixelwar.tests;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.drawing.DrawTile;
import pixelwar.experiment.DrawTilePixelSum;
import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;

public class PixelWarDemo {
	private static final Object mutex = new Object();

	public static void main(String[] args) throws InterruptedException {
	    String resultPath = "data/test/PixelWarDemo.txt";
	    int duration;
	    
	    ExecutorService poolFrance = null;
		ExecutorService poolEspagne = null;
		ImageTreePixelMutex imgFrance = new ImageTreePixelMutex(64);
		ImageTreeInterMutex imgEspagne = new ImageTreeInterMutex(64);

		AtomicInteger cptGlobalFrance = null; 
		AtomicInteger cptGlobalEspagne = null; 

	    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
	    	for (duration = 100; duration <= 10000; duration = duration*2) { // on double la durée à chaque fois
	    		System.out.println("duration = " + duration);
	    		poolFrance = Executors.newFixedThreadPool(100);
	    		poolEspagne = Executors.newFixedThreadPool(100);
	

	 
	        	
	        	cptGlobalFrance = new AtomicInteger();
	        	cptGlobalEspagne = new AtomicInteger();
	        	
			    Thread tFrance, tEspagne;

	        	
	        	/* variables pour le code des threads juste en dessous qui a besoin de variables final */
	        	final ExecutorService poolbis1 = poolFrance;
	        	final ExecutorService poolbis2 = poolEspagne;
	        	final int durationbis = duration;
	        	//System.out.println("duration = " + duration);
	        	/* Démarrer des threads qui arrêteront les pools après un certain délai */ 
	        	tFrance = new Thread(() -> {
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
	        	tFrance.start();
	        	System.out.println("duration = " + duration);
	        	/* chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() */
		    	for(int j = 0; (j < 100) && (!poolFrance.isShutdown()); j++) {
		    		synchronized(mutex) {
		    			poolFrance.submit(new DrawTilePixelSum(imgFrance, 8, cptGlobalFrance, Color.BLUE));
		    			poolFrance.submit(new DrawTilePixelSum(imgFrance, 8, cptGlobalFrance, Color.WHITE));
		    			poolFrance.submit(new DrawTilePixelSum(imgFrance, 8, cptGlobalFrance, Color.RED));


		    		}
		    	}
		    	tFrance.join();
		    	System.out.println("duration = " + duration);
		    	tEspagne = new Thread(() -> {
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
	        	tEspagne.start();
	        	System.out.println("duration = " + duration);
	        	/* chaque thread du pool va produire des tuiles et les poser jusqu'à être interrompu par le pool.shutdown() */
		    	for(int j = 0; (j < 100) && (!poolEspagne.isShutdown()); j++) {
		    		synchronized(mutex) {
		    			poolEspagne.submit(new DrawTilePixelSum(imgEspagne, 8, cptGlobalEspagne, new Color(0xDC,0x14,  0x3C))); // crimson color
		    			poolEspagne.submit(new DrawTilePixelSum(imgEspagne, 8, cptGlobalEspagne, Color.YELLOW));
		    			poolEspagne.submit(new DrawTilePixelSum(imgEspagne, 8, cptGlobalEspagne, new Color(0xDC,0x14,  0x3C)));


		    		}
		    	}
		    	tEspagne.join();
		    	
		    	out.write(duration + " " + cptGlobalFrance.get() + " " + cptGlobalEspagne.get()+ "\n");
		    	
		    	
		    	
		    	
		    	
		    }
	    	
	    } catch (IOException e) {
    		e.printStackTrace();
    	}
	    String pathE = "data/test/imgEspagne.txt";
    	System.out.println("Ouvrir le fichier " + pathE + " pour voir l'image résultat pour l'Espagne");
		imgEspagne.exportImageColor(pathE);
		
		String pathF = "data/test/imgFrance.txt";
    	System.out.println("Ouvrir le fichier " + pathF + " pour voir l'image résultat pour la France");
		imgFrance.exportImageColor(pathF);
		
	    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");

	}

}
