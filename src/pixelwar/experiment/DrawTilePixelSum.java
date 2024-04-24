package pixelwar.experiment;

import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;

public class DrawTilePixelSum implements Runnable {
	private int sizeTile;
	private final ImageTree t;
	private int cpt = 0; // compte les tuiles posées par ce thread
	private AtomicInteger cptGlobal; // variable commune à tous les threads pour agréger le nombre total de tuiles qu'ils ont posées
	
	public DrawTilePixelSum(ImageTree t, int sizeTile, AtomicInteger cpt) {
		this.t = t;
		this.sizeTile = sizeTile;
		this.cptGlobal = cpt;
	}

	/* Produit des tuiles et les pose tant que le thread n'est pas interrompu par la fermeture du pool */
	@Override
	public void run(){
		while(!Thread.currentThread().isInterrupted()) {
			t.putTile(new Tile(t, sizeTile));
			cpt++;
		}
		cptGlobal.addAndGet(cpt); // ajouter à la variable commune le nombre de tuile qu'on a posées
	}
	
}




/*
private final Map<Integer, Integer> map;
private Tile tile;
*/

/*
public DrawTilePixelSum(ImageTree t, int sizeTile, Map<Integer, Integer> map) {
	this.tile = new Tile(t, sizeTile);
	this.t = t;
	this.map = map;
}
*/

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
