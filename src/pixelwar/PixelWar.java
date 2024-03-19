package pixelwar;

import java.util.concurrent.Executors;

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;

import java.util.concurrent.ExecutorService;

public class PixelWar {

	/* test big lock :
	 * public static void main0(String[] args) {
	
		ImageTreeMutex img = new ImageTreeMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for( int i = 0; i < 2; i++) {
			//Utils.inverser(i);
		}
		//Utils.inverser(2147483647);
		pool.submit(new DrawPixel(8, img));
		//ImageTreeMutex.showTree(img.getroot());
		//(Runnable) ()-> {img.putPixel(0) }
	}*/
	
	// test des tuiles avec stratégie 1 & 3 
	public static void main3(String[] args) {
		int[] ids = {0, 1, 2, 3};
		int[] ids2 = {3, 6, 7};
		int[] ids3 = {3, 6, 7, 4};
		
		int N = 4;
		//Tile t1 = new Tile(ids);
		//Tile t2 = new Tile(ids2);
		//Tile t3 = new Tile(ids3);

		ImageTreeMutex img = new ImageTreeMutex(8);
		
		//Tile t = new Tile(img.getN(), img);
		Tile tm = new Tile(img.getN(), img.getMatrix());

		//System.out.println(t.toString());
		System.out.println(tm.toString());


		//ImageTreePixelMutex img = new ImageTreePixelMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		pool.submit(new DrawTile(tm, img));
		/*
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
		pool.shutdown();
		//(Runnable) ()-> {img.putPixel(0) }
		 * 
		 */
	}
	
	public static void main5(String[] args) {
		ImageTreeInterMutex img = new ImageTreeInterMutex(2);
		Tile tm = new Tile(img.getN(), img.getMatrix());
		//Tile tm2 = new Tile(img.getN(), img.getMatrix());
		//Tile tm3 = new Tile(img.getN(), img.getMatrix());
		System.out.println(tm.toString());
		/*tm2.toString();
		tm3.toString();*/
		img.pathToNode(tm);
		/*img.pathToNode(tm2);
		img.pathToNode(tm3);*/
	}
	
	//strat 2
	public static void main(String[] args) {
		ImageTreeInterMutex img = new ImageTreeInterMutex(2);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		for(int i = 0; i<10; i++) {
			
			pool.submit(new DrawTile(new Tile(img.getN(), img.getMatrix()), img));
		}
		pool.shutdown();
	}

	// matrice 
	public static void main1(String[] args) throws Exception {
		
		ImageTreeMutex img = new ImageTreeMutex(32);
		
		for(int i = 0; i < img.getN(); i++) {
			for(int j = 0; j< img.getN(); j++) {
				System.out.println("Looking for pixel : (" + i + "," + j + ")");
				
				Pixel p = img.findPixel(i, j);
				System.out.println("Found pixel : " + p.toString() + "\n");
				
				if(p.sameCoordinates(i, j) == false) {
					throw new Exception("Echec du test de findPixel");
				}
			}
		}
		
		System.out.println("\n\nAffichage de la matrice : \n");
		img.createMatrix();
		img.showMatrix();
		
		String filename = "essai.txt";
		img.exportImage(filename);
		System.out.println("\n\nOuvrir le fichier '" + filename + "' pour voir le résultat de l'export");
		
	}
	
}
