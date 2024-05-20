package pixelwar.experiment;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreePixelMutex;
import pixelwar.tree.ImageTree;


public class TimeExperiment3 {
	
		
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		/* Arbres & pools pour les graphes selon les paramètres à faire varier */
	 		ImageTree img3 = null;
	 		
			ExecutorService pool3 = null;
			
			/*Valeurs maximum des métriques à changer selon les caractéristiques matérielles*/
			int maxToile = 512; // max 512 pour 16Go RAM, max 4096 pour 32Go RAM
			int maxThreads = 1000; // si durée d'execution trop longue, réduire nombre de threads
			
			/* Paramètres des expérimentations */
			int tailleTuile = 16;
		    int nbThreads = 20;
		    int tailleToile = 512;
			
		    /* fichier de sortie */
		    String resultPath;

	 		Random rand = new Random(); // pour la couleur de tuile (r,g,b aléatoires)
		    
			System.out.println("Stratégie PixelLock");
 		    	    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 6;
		    
		    resultPath = "data/time/testImgSize_time3.txt";


		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleToile = 8; tailleToile <= maxToile; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        
		        	out.write(tailleToile + " ");
		        			        	
			    	for(int j = 0; j < 100 ; j++) {
			    		
			    		Future<Long> result3 = pool3.submit(new DrawTileTime(img3, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
			    		double res = (double) result3.get();
			    		out.write(res + " ");		 
			    		
			    	} 
			    	out.write("\n");
			    	
			    	pool3.shutdown();
				    pool3.awaitTermination(15, TimeUnit.SECONDS);
				    
				    
			    }
		
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
	    	
	
			 
			// On fait varier la taille de la tuile (taille tuile Max : 256 pour 32Go RAM, 128 pour 16Go RAM)
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taille de la tuile");
		 
		    
			resultPath = "data/time/testTileSize_time3.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleTuile = 2; tailleTuile <= 128; tailleTuile *= 2) {
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img3 = new ImageTreePixelMutex(tailleToile);
		        	out.write(tailleTuile + " ");

		        	for(int j = 0; j < 100 ; j++) {

			    		Future<Long> result3 = pool3.submit(new DrawTileTime(img3, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
			    		double res = (double) result3.get();
			    		out.write(res + " ");		 
			    	}
			    	
			    	out.write("\n");
			    	
			    	pool3.shutdown();
				    pool3.awaitTermination(15, TimeUnit.SECONDS);
				    
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    
		    
		    
		    
		    
		    
		    /*On pose des tuiles de tailles différentes
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On pose des tuiles de tailles différentes");
 			
 			// Paramètres fixes
 			tailleToile = 2048;
 		    nbThreads = 20;
 		    
 		    // Liste des tailles de tuiles 
 		    int[] tailles = {2, 4, 8, 16, 32, 64, 128, 256};
 		    
 			
 			pool3 = Executors.newFixedThreadPool(nbThreads);


        	img3 = new ImageTreeInterMutex(tailleToile);
        
	    	resultPath = "data/time/testTileVariable_time3.txt";
            try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
				// on pose une tuile de chaque taille
		    	for(int i=0; i<tailles.length; i++) {
		        	out.write(tailles[i] + " ");
		        	
			    	for(int j = 0; j<100; j++) {
		    			Future<Long> result3 = pool3.submit(new DrawTileTime(img3, tailles[i]));
			    		out.write(result3.get() + " ");
		    		}
			    	out.write("\n");
		    	}
	    	} catch (IOException e) {
 	    		e.printStackTrace();
 	    	}
	    	
	    	//fermer les pools 
	    	pool3.shutdown();
		    pool3.awaitTermination(15, TimeUnit.SECONDS);
		    
 		    
 	
 		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    
		    */
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 			
 			/* Paramètres fixes */
 			tailleToile = 2048;
 		    tailleTuile = 16;
 		    
 		    
 		    resultPath = "data/time/testNbThreads_time3.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
	 	    	for (nbThreads = 1; nbThreads <= maxThreads; nbThreads += 100) { 
	 	    		pool3 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img3 = new ImageTreePixelMutex(tailleToile);
	
		        	out.write(nbThreads + " ");

	 		    	for(int j = 0; j < 100 ; j++) {
	 		    		Future<Long> result3 = pool3.submit(new DrawTileTime(img3, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
			    		double res = (double) result3.get();
			    		out.write(res + " ");
	 		    	
			    	}
	 		    	
	 		    	out.write("\n");
	 		    	
	 		    	pool3.shutdown();
				    pool3.awaitTermination(15, TimeUnit.SECONDS);
				   
	 	 		    
			}
	 	    	
 		} catch (IOException e) {
    		e.printStackTrace();
    	}
		System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
 		    
	 }
	 	
}
