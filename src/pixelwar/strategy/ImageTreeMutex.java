package pixelwar.strategy;

import java.util.concurrent.locks.ReentrantLock;

import pixelwar.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.Pixel;

public class ImageTreeMutex extends ImageTree {
    private ReentrantLock mutex = new ReentrantLock();
    
    public ImageTreeMutex(int N) {
    	super(N);
    }    

	@Override
	public Long putTile(Tile t) {
		//System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		try {
			long debut = System.nanoTime();
			mutex.lock();
			long fin = System.nanoTime();
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
