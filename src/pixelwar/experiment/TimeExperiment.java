package pixelwar.experiment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.DrawTile;
import pixelwar.Tile;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;

public class TimeExperiment {
	 private static int maxSize = 2<<4;
	 
	 
	// test de stratégie 2 avec ImageTreeMutex : 
	 	public static void main(String[] args) throws InterruptedException, ExecutionException {
	 		ImageTreeMutex img = null;
			ExecutorService pool = null;
			// selon la taille de l'image et le nombre de threads : 
			
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
		    
		    img = new ImageTreeMutex(8);
		    pool = Executors.newFixedThreadPool(20);
		    for (int j = 1; j <= 8; j++) {
		         // boucle pour les tailles d'images
		    	int i = 0;
		    	while (i< 30) {
		    		Future<Long> result = pool.submit(new DrawTileTime(img,j));
		    		System.out.println("Résultat pour une taille d'image : 32*32 " + " taille de tuile : " + j + " et " + 20 + " threads : " + result.get());
		        	System.out.println();
		    		i++;
		    	}
		                
		        
		    }
		    pool.shutdown();
		    pool.awaitTermination(15, TimeUnit.SECONDS);
				//String path = "test_strat2.txt";
				//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
				//img.exportImage(path);
		}
		
	 
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
