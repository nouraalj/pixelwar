package pixelwar.strategy;

import java.util.List;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.Pixel;
import pixelwar.tree.PixelMutex;

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
	public Long putTile(Tile t) {
		List<Pixel> pixels = t.sortById().getPixels(); // trier les pixels par ordre croissant d'identifiant
		//System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		long debut = System.nanoTime();
		for (Pixel p : pixels) {
			p.lockNode();
		}
		long fin = System.nanoTime();
		for (Pixel p : pixels) {
			putPixel(p);
		}
		for (Pixel p : pixels) {
			p.unlockNode();
		}
		System.out.println("\n");
		return fin - debut;
	}
}
