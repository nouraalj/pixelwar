package pixelwar.experiment;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pixelwar.DrawTile;
import pixelwar.Tile;
import pixelwar.strategy.ImageTreePixelMutex;

public class TimeExperiment {
	 private static int maxSize = 2<<4;
	 
	// test de stratégie 2
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
	
}
