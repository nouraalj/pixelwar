package pixelwar.strategy;

import java.util.concurrent.locks.ReentrantLock;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.Pixel;
import pixelwar.Tile;

public class ImageTreeMutex extends ImageTree {
    private ReentrantLock mutex = new ReentrantLock();
    
    public ImageTreeMutex(int N) {
    	super(N);
    }    

	@Override
	public void putTile(Tile t) {
		System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		try {
			mutex.lock();
			for (Pixel p : t.getPixels()) {
				putPixel(p);
			}
			System.out.println("\n");
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
