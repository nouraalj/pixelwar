package pixelwar.strategy;

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
	public Long putTile(Tile t) {
		try {
			long debut = System.nanoTime();
			mutex.lock(); // obtenir le verrou pour l'accès à l'arbre
			long fin = System.nanoTime();
			
			// poser les pixels
			for (Pixel p : t.getPixels()) {
				putPixel(p);
			}
			
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
