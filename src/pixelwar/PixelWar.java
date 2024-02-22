package pixelwar;

import java.util.concurrent.Executors;

import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;

import java.util.concurrent.ExecutorService;

public class PixelWar {

	public static void main0(String[] args) {
		
		ImageTreeMutex img = new ImageTreeMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for( int i = 0; i < 2; i++) {
			Utils.inverser(i);
		}
		Utils.inverser(2147483647);
		pool.submit(new DrawPixel(8, img));
		//ImageTreeMutex.showTree(img.getroot());
		//(Runnable) ()-> {img.putPixel(0) }
	}
	
	public static void main(String[] args) {
		int[] ids = {0, 1, 2, 3};
		int[] ids2 = {3, 6, 7};
		int[] ids3 = {3, 6, 7, 4};

		Tile t1 = new Tile(ids);
		Tile t2 = new Tile(ids2);
		Tile t3 = new Tile(ids3);

		
		ImageTreeMutex img = new ImageTreeMutex(4);
		//ImageTreePixelMutex img = new ImageTreePixelMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		for(int i = 0; i<11; i++) {
			if (i%3 == 0) {
				pool.submit(new DrawTile(t2, img));
			} else if (i%3 == 1) {
				pool.submit(new DrawTile(t3, img));
			} else {
				pool.submit(new DrawTile(t1, img));
			}
		}
		ImageTreeMutex.showTree(img.getroot());
		//(Runnable) ()-> {img.putPixel(0) }
	}
}
