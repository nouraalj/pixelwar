package pixelwar.experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreeMutex;
import pixelwar.tree.ImageTree;

public class TimeExperiment1 {
	
		
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		/* Arbres & pools pour les graphes selon les paramètres à faire varier */
	 		ImageTree img1 = null;
	 		
			ExecutorService pool1 = null;

			
			/* Paramètres des expérimentations */
			int tailleTuile;
		    int nbThreads;
		    int tailleToile;
			
		    /* fichier de sortie */
		    String resultPath;

		    		    
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 2;
		    nbThreads = 20;
		    
		    resultPath = "data/time/testImgSize_time1.txt";


		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleToile = 2; tailleToile <= 512; tailleToile <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleToile);
		        
		        	out.write(tailleToile + " ");
		        			        	
			    	for(int j = 0; j < 100 ; j++) {
			    		
			    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
			    		double res = (double) result1.get();
			    		out.write(res + " ");		 
			    		
			    	} 
			    	out.write("\n");
			    	
			    	pool1.shutdown();
				    pool1.awaitTermination(15, TimeUnit.SECONDS);
				    
				    
			    }
		
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
	    	
	
			 
			// On fait varier la taille de la tuile
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taille de la tuile");
			
			/* Paramètres fixes */
			tailleToile = 32;
		    nbThreads = 20;
		 
		    
			resultPath = "data/time/testTileSize_time1.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
		    	for (tailleTuile = 1; tailleTuile < tailleToile; tailleTuile += 2) {
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img1 = new ImageTreeMutex(tailleToile);
		        	out.write(tailleTuile + " ");

		        	for(int j = 0; j < 100 ; j++) {

			    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
			    		double res = (double) result1.get();
			    		out.write(res + " ");		 
			    	}
			    	
			    	out.write("\n");
			    	
			    	pool1.shutdown();
				    pool1.awaitTermination(15, TimeUnit.SECONDS);
				    
			    }
		    	
		    } catch (IOException e) {
	    		e.printStackTrace();
	    	}
		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
		    
		    
		    
		    
		    
		    
		    //On pose des tuiles de tailles différentes
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On pose des tuiles de tailles différentes");
 			
 			// Paramètres fixes
 			tailleToile = 1024;
 		    nbThreads = 20;
 		    
 		    // Liste des tailles de tuiles 
 		    int[] tailles = {2, 4, 8, 16, 32, 64, 128};

 		    
 			
 			pool1 = Executors.newFixedThreadPool(nbThreads);


        	img1 = new ImageTreeMutex(tailleToile);
        
	    	resultPath = "data/time/testTileVariable_time1.txt";
            try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
				// on pose une tuile de chaque taille
		    	for(int i=0; i<tailles.length; i++) {
		    		//System.out.println(i);
		        	out.write(tailles[i] + " ");
		        	
			    	for(int j = 0; j<100; j++) {
		    			Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailles[i]));
			    		out.write(result1.get() + " ");
		    		}
			    	out.write("\n");
		    	}
	    	} catch (IOException e) {
 	    		e.printStackTrace();
 	    	}
	    	
	    	//fermer les pools 
	    	pool1.shutdown();
		    pool1.awaitTermination(15, TimeUnit.SECONDS);
		    
 		    
 	
 		    System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
	
	    
		    
		    
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 			
 			/* Paramètres fixes */
 			tailleToile = 128;
 		    tailleTuile = 4;
 		    
 		    
 		    resultPath = "data/time/testNbThreads_time1.txt";
		    try (BufferedWriter out = new BufferedWriter(new FileWriter(resultPath))) {
	 	    	for (nbThreads = 1; nbThreads <= 20; nbThreads++) { 
	 	    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		
		        	img1 = new ImageTreeMutex(tailleToile);
	
		        	out.write(nbThreads + " ");

	 		    	for(int j = 0; j < 100 ; j++) {
	 		    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
			    		double res = (double) result1.get();
			    		out.write(res + " ");
	 		    	
			    	}
	 		    	
	 		    	out.write("\n");
	 		    	
	 		    	pool1.shutdown();
				    pool1.awaitTermination(15, TimeUnit.SECONDS);
				   
	 	 		    
			}
	 	    	
 		} catch (IOException e) {
    		e.printStackTrace();
    	}
		System.out.println("Ouvrir le fichier " + resultPath + " pour voir les résultats bruts");
 		    
	 }
	 	
}
