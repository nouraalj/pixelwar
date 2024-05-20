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

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.tree.ImageTree;


public class TimeExperiment2 {
	
		
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

	 		Random rand = new Random(); // pour la couleur de tuile (r,g,b aléatoires)
		    		    
			System.out.println("Stratégie InterLock");

			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 6;
		    nbThreads = 20;
		    
		    resultPath = "data/time/testImgSize_time2.txt";


		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleToile = 8; tailleToile <= maxToile; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
	
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        
		        	out.write(tailleToile + " ");
		        			        	
			    	for(int j = 0; j < 100 ; j++) {
			    		
			    		Future<Long> result2 = pool2.submit(new DrawTileTime(img2, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
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
	    	
	
			 
			// On fait varier la taille de la tuile
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taille de la tuile");
			
		    
			resultPath = "data/time/testTileSize_time2.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleTuile = 2; tailleTuile <= tailleToile; tailleTuile *= 2) {
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img2 = new ImageTreeInterMutex(tailleToile);
		        	out.write(tailleTuile + " ");

		        	for(int j = 0; j < 100 ; j++) {

			    		Future<Long> result2 = pool2.submit(new DrawTileTime(img2, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
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
		    
		    
		    
		    
		    
		    
		    /*On pose des tuiles de tailles différentes
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On pose des tuiles de tailles différentes");
 			
 			// Paramètres fixes
 			tailleToile = 2048;
 		    nbThreads = 20;
 		    
 		    // Liste des tailles de tuiles 
 		    int[] tailles = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
 		    
 			
 			pool2 = Executors.newFixedThreadPool(nbThreads);


        	img2 = new ImageTreeInterMutex(tailleToile);
        
	    	resultPath = "data/time/testTileVariable_time2.txt";
            try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
				// on pose une tuile de chaque taille
		    	for(int i=0; i<tailles.length; i++) {
		    		//System.out.println(i);
		        	out.write(tailles[i] + " ");
		        	
			    	for(int j = 0; j<100; j++) {
		    			Future<Long> result2 = pool2.submit(new DrawTileTime(img2, tailles[i]));
			    		out.write(result2.get() + " ");
		    		}
			    	out.write("\n");
		    	}
	    	} catch (IOException e) {
 	    		e.printStackTrace();
 	    	}
	    	
	    	//fermer les pools 
	    	pool2.shutdown();
		    pool2.awaitTermination(15, TimeUnit.SECONDS);
		    
 		    
 	
 		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    
		    */
		    		    
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 		    
 		    tailleToile = 128; // 2048 pour 32Go RAM
 		    tailleTuile = 12;
 		    resultPath = "data/time/testNbThreads_time2.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
	 	    	for (nbThreads = 1; nbThreads <= maxThreads; nbThreads += 100) { 
	 	    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img2 = new ImageTreeInterMutex(tailleToile);
	
		        	out.write(nbThreads + " ");

	 		    	for(int j = 0; j < 100 ; j++) {
	 		    		Future<Long> result2 = pool2.submit(new DrawTileTime(img2, tailleTuile, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
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
		System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    
	 }
	 	
}
