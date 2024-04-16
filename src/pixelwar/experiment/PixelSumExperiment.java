package pixelwar.experiment;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreeMutex;

public class PixelSumExperiment {

	
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
		        	 Map<Integer, Integer> map = new HashMap<>();
		        	 img = new ImageTreeMutex(i);
		        	 pool = Executors.newFixedThreadPool(j);
		        	 pool.submit(new DrawTilePixelSum(img, 2, map));

		             pool.shutdown();
		             pool.awaitTermination(15, TimeUnit.SECONDS);
		             for(Entry<Integer, Integer> k : map.entrySet()) {
		            	 System.out.print(" thread :" + k.getKey() + " pixels posés : " + k.getValue()+ " ");
		             }
		         }
		    }

				//String path = "test_strat2.txt";
				//System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
				//img.exportImage(path);
		}
		
}
