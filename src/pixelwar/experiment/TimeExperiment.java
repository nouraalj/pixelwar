package pixelwar.experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;
import pixelwar.tree.ImageTree;
import pixelwar.utils.Utils;

public class TimeExperiment {
	
		
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		/* Arbres & pools pour les graphes selon les paramètres à faire varier */
	 		ImageTree img1 = null;
	 		ImageTree img2 = null;
	 		ImageTree img3 = null;

			ExecutorService pool1 = null;
			ExecutorService pool2 = null;
			ExecutorService pool3 = null;
			
			/* Paramètres des expérimentations */
			int tailleTuile;
		    int nbThreads;
		    int tailleArbre;
			
		    
		    
			// On fait varier la taille de l'arbre
			System.out.println("On fait varier la taille de l'arbre");
			
			/* Paramètres fixes */
		    tailleTuile = 2;
		    nbThreads = 20;
		    
		    /* Listes des résultats pour chaque stratégie */
		    Long[] l11 = new Long[100];
		    Long[] l12 = new Long[100];		    
		    Long[] l13 = new Long[100];
		    
		     
		    try (BufferedWriter out = new BufferedWriter(new FileWriter("testImgSize.txt"))) {
		    	for (tailleArbre = 2; tailleArbre <= 512; tailleArbre <<= 1) { // la taille de la tuile doit être inférieure ou égale à celle de la toile
		    		pool1 = Executors.newFixedThreadPool(nbThreads);
		    		pool2 = Executors.newFixedThreadPool(nbThreads);
		    		pool3 = Executors.newFixedThreadPool(nbThreads);
	
		        	img1 = new ImageTreeMutex(tailleArbre);
		        	img2 = new ImageTreeInterMutex(tailleArbre);
		        	img3 = new ImageTreePixelMutex(tailleArbre);
		        	
	
		        	
			    	for(int j = 0; j < 100 ; j++) {
			    		
			    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
			    		l11[j] = result1.get();
			    		//récupérer le résultat ici dans un fichier			    		
			    		/*
			    		Future<Long> result2= pool2.submit(new DrawTileTime(img2, tailleTuile));
			    		l12[j] = result2.get();
	
			    		Future<Long> result3= pool3.submit(new DrawTileTime(img3, tailleTuile));
			    		l13[j] = result3.get();
						*/
			    		//System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result11.get());
			    	} 
			    	Arrays.sort(l11);
			    	
		    		out.write(Utils.stats(tailleArbre, l11));
			    	
			    	pool1.shutdown();
				    pool1.awaitTermination(15, TimeUnit.SECONDS);
				    
				    pool2.shutdown();
				    pool2.awaitTermination(15, TimeUnit.SECONDS);
				    
				    pool3.shutdown();
				    pool3.awaitTermination(15, TimeUnit.SECONDS);
				    
			    }
			    out.close();
		
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	
	    	 Long[] l21 = new Long[100];
			 Long[] l22 = new Long[100];		    
			 Long[] l23 = new Long[100];
		    
			// On fait varier la taille de la tuile
			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("On fait varier la taille de la tuile");
			
			/* Paramètres fixes */
		    tailleArbre = 16;
		    nbThreads = 20;
		    
	    	for (tailleTuile = 1; tailleTuile < tailleArbre; tailleTuile++) {
	    		pool1 = Executors.newFixedThreadPool(nbThreads);
	    		pool2 = Executors.newFixedThreadPool(nbThreads);
	    		pool3 = Executors.newFixedThreadPool(nbThreads);

	        	img1 = new ImageTreeMutex(tailleArbre);
	        	img2 = new ImageTreeInterMutex(tailleArbre);
	        	img3 = new ImageTreePixelMutex(tailleArbre);

	        	
		    	for(int j = 0; j < 100 ; j++) {
		    		
		    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
		    		l21[j] = result1.get();
		    		System.out.println(l21[j]);
		    		
		    		Future<Long> result2= pool2.submit(new DrawTileTime(img2, tailleTuile));
		    		l22[j] = result2.get();

		    		Future<Long> result3= pool3.submit(new DrawTileTime(img3, tailleTuile));
		    		l23[j] = result3.get();
		    		
		    		//System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result1.get());
		    	}
		    	
		    	pool1.shutdown();
			    pool1.awaitTermination(15, TimeUnit.SECONDS);
			    
			    pool2.shutdown();
			    pool2.awaitTermination(15, TimeUnit.SECONDS);
			    
			    pool3.shutdown();
			    pool3.awaitTermination(15, TimeUnit.SECONDS);
			    
		    }
	    	//récupérer le résultat ici dans un fichier
	    	try {
	    		out = new BufferedWriter(new FileWriter("testTileSize.txt"));
	    		for (int i = 0; i < l21.length; ++i) {
	    			out.write(l21[i] + " " + l22[i] + " " + l23[i] + "\n");
	    		}
	    		out.close();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	
	    	
	    	 Long[] l31 = new Long[100];
			 Long[] l32 = new Long[100];		    
			 Long[] l33 = new Long[100];
		    
		    // On fait varier le nombre de threads
 			System.out.println("\n\n\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 			System.out.println("On fait varier le nombre de threads");
 			
 			/* Paramètres fixes */
 		    tailleArbre = 32;
 		    tailleTuile = 10;
 		    
 	    	for (nbThreads  = 1; nbThreads <= 100; nbThreads++) { 
 	    		pool1 = Executors.newFixedThreadPool(nbThreads);
	    		pool2 = Executors.newFixedThreadPool(nbThreads);
	    		pool3 = Executors.newFixedThreadPool(nbThreads);

	        	img1 = new ImageTreeMutex(tailleArbre);
	        	img2 = new ImageTreeInterMutex(tailleArbre);
	        	img3 = new ImageTreePixelMutex(tailleArbre);

 	        	
 		    	for(int j = 0; j < 5 ; j++) {
 		    		
 		    		Future<Long> result1 = pool1.submit(new DrawTileTime(img1, tailleTuile));
		    		l31[j] = result1.get();
		    		
		    		Future<Long> result2= pool2.submit(new DrawTileTime(img2, tailleTuile));
		    		l32[j] = result2.get();

		    		Future<Long> result3= pool3.submit(new DrawTileTime(img3, tailleTuile));
		    		l33[j] = result3.get();
		    		
 		    		//System.out.println("Résultat pour une taille d'image : " + tailleArbre + "*" + tailleArbre + ", taille de tuile : " + tailleTuile + ", et " + nbThreads + " threads : " + result.get());
 		    	}
 		    	
 		    	pool1.shutdown();
			    pool1.awaitTermination(15, TimeUnit.SECONDS);
			    
			    pool2.shutdown();
			    pool2.awaitTermination(15, TimeUnit.SECONDS);
			    
			    pool3.shutdown();
			    pool3.awaitTermination(15, TimeUnit.SECONDS);
 	 		    
		}
 	    	//récupérer le résultat ici dans un fichier
 	    	try {
 	    		out = new BufferedWriter(new FileWriter("testNbThreads.txt"));
 	    		for (int i = 0; i < l31.length; ++i) {
 	    			out.write(l31[i] + " " + l32[i] + " " + l33[i] + "\n");
 	    		}
 	    		out.close();
 	    	} catch (IOException e) {
 	    		// TODO Auto-generated catch block
 	    		e.printStackTrace();
 	    	}
	 	}
}
