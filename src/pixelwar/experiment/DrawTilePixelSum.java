package pixelwar.experiment;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;

public class DrawTilePixelSum implements Runnable {
	private final Tile tile;
	private final ImageTree t;
	private AtomicInteger cpt;
	//private final Map<Integer, Integer> map; // sert à enregistrer le nombre de tuiles effectivement posées
	
	public DrawTilePixelSum(ImageTree t, int sizeTile, AtomicInteger cpt) {
		this.tile = new Tile(t, sizeTile);
		this.t = t;
		this.cpt = cpt;
	}
	
	/*
	public DrawTilePixelSum(ImageTree t, int sizeTile, Map<Integer, Integer> map) {
		this.tile = new Tile(t, sizeTile);
		this.t = t;
		this.map = map;
	}
	*/

	@Override
	public void run(){
		t.putTile(tile);
		cpt.incrementAndGet();
	}
	
	/*
	 * @Override
	public void run(){
		t.putTile(tile);
		
		Integer pixelPut = tile.nbPixels();
		Integer tmp = map.putIfAbsent((int) Thread.currentThread().getId(), pixelPut);
		if (tmp != null) {
			map.put((int) Thread.currentThread().getId(), tmp+pixelPut);
		}
	}
	 */
}
