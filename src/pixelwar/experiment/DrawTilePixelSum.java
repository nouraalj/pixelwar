package pixelwar.experiment;

import java.util.Map;

import pixelwar.Tile;
import pixelwar.tree.ImageTree;

public class DrawTilePixelSum implements Runnable {
	private final Tile tile;
	private final ImageTree t;
	private final Map<Integer, Integer> map;
	
	public DrawTilePixelSum(ImageTree t, int sizeTile, Map<Integer, Integer> map) {
		/*id (tuile) pixel à poser*/
		Tile tile = new Tile(t, sizeTile);
		this.tile = tile;
		this.t = t;
		this.map = map;
	}

	@Override
	public void run(){
		t.putTile(tile);
		Integer pixelPut = tile.nbPixels();
		Integer tmp = map.putIfAbsent((int) Thread.currentThread().getId(), pixelPut);
		if (tmp != null) {
			map.put((int) Thread.currentThread().getId(), tmp+pixelPut);
		}
	}
}
