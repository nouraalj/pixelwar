package pixelwar.strategy;

import java.awt.Color;
import java.util.concurrent.locks.ReentrantLock;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.Pixel;

/* Classe de représentation de l'arbre pour la stratégie 1 */ 

public class ImageTreeMutex extends ImageTree {
    private ReentrantLock mutex = new ReentrantLock(); // verrou pour l'accès à l'arbre
    
    public ImageTreeMutex(int N) {
    	super(N);
    } 

    /* Pose la tuile et retourne le temps mis pour obtenir les verrous nécessaires */
	@Override
	public Long putTile(Tile t, Color c) {
		try {
			long debut = System.nanoTime();
			mutex.lock(); // obtenir le verrou pour l'accès à l'arbre
			long fin = System.nanoTime();
			
			// poser les pixels
			for (Pixel p : t.getPixels()) {
				putPixelColor(p,c);
			}
			
			return fin - debut;
			
		} finally {
			mutex.unlock();
		}
	}
	
	public Long putTileCS(Tile t, Color c) {
		try {
			
			mutex.lock(); // obtenir le verrou pour l'accès à l'arbre
			long debut = System.nanoTime();
			// poser les pixels
			for (Pixel p : t.getPixels()) {
				putPixelColor(p,c);
			}
			long fin = System.nanoTime();

			return fin - debut;
			
		} finally {
			mutex.unlock();
		}
	}
	
	@Override
	public Pixel createPixel(int id, int x, int y) {
		return new Pixel(id, x, y);
	}

	@Override
	public InterNode createInterNode() {
		return new InterNode();
	}
	
}
