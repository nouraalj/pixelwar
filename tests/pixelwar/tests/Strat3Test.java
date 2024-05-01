package pixelwar.tests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pixelwar.drawing.DrawTile;
import pixelwar.strategy.ImageTreePixelMutex;

public class Strat3Test {

	public static void main(String[] args) throws Exception {
		ImageTreePixelMutex img = new ImageTreePixelMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		for(int i = 0; i<20; i++) {
			pool.submit(new DrawTile(img, 2));
		}
		
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES); // attend que tous les threads aient terminé
		
		String path = "data/test/test_strat3.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);

	}

}
