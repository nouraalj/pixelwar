package pixelwar.experiment;

import java.util.Date;
import java.util.concurrent.Callable;

import pixelwar.ImageTree;
import pixelwar.Tile;

public class DrawTileTime implements Callable<Long> {
	private final Tile tile;
	private final ImageTree t;
	
	public DrawTileTime(Tile tile, ImageTree t) {
		/*id (tuile) pixel à poser*/
		this.tile = tile;
		this.t = t;
	}

	@Override
	public Long call() throws Exception {
		long debut = new Date().getTime();
		
		t.putTile(tile);
		long fin = new Date().getTime();
		System.out.println("temps :" + (fin-debut));
		return (Long) fin-debut;
	}

}
