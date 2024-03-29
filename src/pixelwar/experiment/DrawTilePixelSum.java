package pixelwar.experiment;

import java.util.concurrent.Callable;

import pixelwar.ImageTree;
import pixelwar.Tile;

public class DrawTilePixelSum implements Callable<Integer> {
	private final Tile tile;
	private final ImageTree t;
	
	public DrawTilePixelSum(Tile tile, ImageTree t) {
		/*id (tuile) pixel à poser*/
		this.tile = tile;
		this.t = t;
	}

	@Override
	public Integer call() throws Exception {
		t.putTile(tile);
		Integer pixelPut = tile.nbPixels();
		return pixelPut;
	}
}
