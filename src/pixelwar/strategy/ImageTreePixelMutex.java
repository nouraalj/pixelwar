package pixelwar.strategy;

import java.awt.Color;
import java.util.List;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.Pixel;
import pixelwar.tree.PixelMutex;

/* Classe de représentation de l'arbre pour la stratégie 3 */ 

public class ImageTreePixelMutex extends ImageTree {
	
	public ImageTreePixelMutex(int N) {
		super(N);
	}
	
	@Override
	public Pixel createPixel(int id, int x, int y) {
		return new PixelMutex(id, x, y);
	}

	@Override
	public InterNode createInterNode() {
		return new InterNode();
	}

	@Override
	public Long putTile(Tile t, Color c) {
		List<Pixel> pixels = t.sortById().getPixels(); // trier les pixels par ordre croissant d'identifiant
		try {
			/* obtenir les verrous de tous les pixels */
			long debut = System.nanoTime();
			for (Pixel p : pixels) {
				p.lockNode();
			}
			long fin = System.nanoTime();
			
			/* poser les pixels */
			for (Pixel p : pixels) {
				putPixelColor(p, c);
			}
			
			return fin - debut;
			
		} finally {
			/* relâcher tous les verrous */
			for (Pixel p : pixels) {
				p.unlockNode();
			}
		}
	}
	
	public Long putTileCS(Tile t, Color c) {
		List<Pixel> pixels = t.sortById().getPixels(); // trier les pixels par ordre croissant d'identifiant
		try {
			/* obtenir les verrous de tous les pixels */
			for (Pixel p : pixels) {
				p.lockNode();
			}
			long debut = System.nanoTime();

			/* poser les pixels */
			for (Pixel p : pixels) {
				putPixelColor(p, c);
			}
			long fin = System.nanoTime();

			return fin - debut;
			
		} finally {
			/* relâcher tous les verrous */
			for (Pixel p : pixels) {
				p.unlockNode();
			}
		}
	}
}
