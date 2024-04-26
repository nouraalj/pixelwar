package pixelwar.tests;

import pixelwar.drawing.Tile;
import pixelwar.strategy.ImageTreeMutex;

public class TileTest {

	public static void main(String[] args) {
		
		ImageTreeMutex img = new ImageTreeMutex(16);
		
		System.out.println("Test de sortById : \n");
		
		for(int i = 0; i<5; i++) {
			Tile t = new Tile(img, 4);
			System.out.println("Tuile avant tri par ordre croissant :\n" + t.toString());
			t = t.sortById();
			System.out.println("Tuile après tri par ordre croissant :\n" + t.toString());
			System.out.println();
		}

	}

}
