package pixelwar.tests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pixelwar.drawing.DrawTile;
import pixelwar.strategy.ImageTreeMutex;

public class Strat1Test {

	public static void main(String[] args) throws Exception {
		ImageTreeMutex img = new ImageTreeMutex(16);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		for(int i = 0; i<15; i++) {
			pool.submit(new DrawTile(img, 2));
		}
		
		pool.shutdown();
		pool.awaitTermination(3, TimeUnit.SECONDS); // attend que tous les threads aient terminé
		
		String path = "data/test/test_strat1.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);

	}

}
