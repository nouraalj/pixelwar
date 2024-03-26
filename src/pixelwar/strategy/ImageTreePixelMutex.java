package pixelwar.strategy;

import java.util.List;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.Pixel;
import pixelwar.PixelMutex;
import pixelwar.Tile;

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
	public void putTile(Tile t) {
		List<Pixel> pixels = t.sortById().getPixels(); // trier les pixels par ordre croissant d'identifiant
		System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		for (Pixel p : pixels) {
			p.lockNode();
			putPixel(p);
			p.unlockNode();
		}
		System.out.println("\n");
	}
}
