package pixelwar.tests;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pixelwar.drawing.DrawTile;
import pixelwar.strategy.ImageTreeInterMutex;

public class Strat2Test {

	public static void main(String[] args) throws Exception {
		ImageTreeInterMutex img = new ImageTreeInterMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		Random rand = new Random();
		for(int i = 0; i<20; i++) {
			pool.submit(new DrawTile(img, 2, new Color(rand.nextInt(), rand.nextInt(), rand.nextInt())));
		}
		pool.shutdown();
		pool.awaitTermination(15, TimeUnit.SECONDS);
		
		String path = "data/test/test_strat2.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);

	}

}
