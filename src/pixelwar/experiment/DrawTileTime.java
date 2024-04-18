package pixelwar.experiment;

import java.util.Date;
import java.util.concurrent.Callable;

import pixelwar.Tile;
import pixelwar.tree.ImageTree;

public class DrawTileTime implements Callable<Long> {
	private final Tile tile;
	private final ImageTree t;
	
	/*
	public DrawTileTime(Tile tile, ImageTree t) {
		this.tile = tile;
		this.t = t;
	}
	*/
	
	public DrawTileTime(ImageTree t, int sizeTile) {
		//construction de la tuile :
		Tile tile = new Tile(t, sizeTile);
		
		this.tile = tile;
		this.t = t;
	}
	
	
	 @Override
	 public Long call() throws Exception {
	     return t.putTile(tile);
	 }
}
