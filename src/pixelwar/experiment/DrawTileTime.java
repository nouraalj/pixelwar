package pixelwar.experiment;

import java.util.Date;
import java.util.concurrent.Callable;

import pixelwar.ImageTree;
import pixelwar.Tile;

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
		 long debut = System.nanoTime();
		 
		 t.putTile(tile);

	     long fin = System.nanoTime();

	     //System.out.println("temps :" + (fin - debut) + " ns");

	     return fin - debut;
	 }

	/*
	@Override
	public Long call() throws Exception {
		long debut = new Date().getTime();
		
		t.putTile(tile);
		long fin = new Date().getTime();
		System.out.println("temps :" + (fin-debut));
		return (Long) fin-debut;
	}
	*/
}
