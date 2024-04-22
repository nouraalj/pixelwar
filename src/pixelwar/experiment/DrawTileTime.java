package pixelwar.experiment;

import java.util.Date;
import java.util.concurrent.Callable;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;

public class DrawTileTime implements Callable<Long> {
	private final Tile tile;
	private final ImageTree t;
	
	public DrawTileTime(ImageTree t, int sizeTile) {
		//construction de la tuile
		this.tile = new Tile(t, sizeTile);
		this.t = t;
	}
	
	 @Override
	 public Long call() throws Exception {
	     return t.putTile(tile);
	 }
}
