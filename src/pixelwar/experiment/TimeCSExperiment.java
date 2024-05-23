package pixelwar.experiment;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.tree.ImageTree;

public class TimeCSExperiment {
		public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		/* Arbres & pools pour les graphes selon les paramètres à faire varier */
	 		ImageTree img2 = null;
	 		
			ExecutorService pool2 = null;
			
			/*Valeurs maximum des métriques à changer selon les caractéristiques matérielles*/
			int maxToile = 512; // max 512 pour 16Go RAM, max 4096 pour 32Go RAM
			// si durée d'execution trop longue, réduire nombre de threads et modifier le pas de boucle de la derniere expérimentation :
			int maxThreads = 1000; 
			
			/* Paramètres des expérimentations */
			int tailleTuile = 16;
		    int nbThreads = 20;
		    int tailleToile = 512; // 2048 pour 32Go de RAM
			
		    /* fichier de sortie */
		    String resultPath;
		    		    
			System.out.println("Stratégie InterLock");
	
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 6;
		    nbThreads = 20;
		    
		    resultPath = "data/time/testImgSize_timeCS2.txt";
	
	
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleToile = 8; tailleToile <= maxToile; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
	
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        
		        	out.write(tailleToile + " ");
		        			        	
			    	for(int j = 0; j < 100 ; j++) {
			    		
			    		Future<Long> result2 = pool2.submit(new DrawTileCSTime(img2, tailleTuile, new Color((int)(Math.random() * 0x1000000))));
			    		double res = (double) result2.get();
			    		out.write(res + " ");		 
			    		
			    	} 
			    	out.write("\n");
			    	
			    	pool2.shutdown();
				    pool2.awaitTermination(15, TimeUnit.SECONDS);
				    
				    
			    }
		
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		}
}
