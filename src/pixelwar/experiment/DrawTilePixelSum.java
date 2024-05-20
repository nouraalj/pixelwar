package pixelwar.experiment;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;

public class DrawTilePixelSum implements Runnable {
	private int sizeTile;
	private final ImageTree t;
	private int cpt = 0; // compte les tuiles posées par ce thread
	private final Color c;
	private AtomicInteger cptGlobal; // variable commune à tous les threads pour agréger le nombre total de tuiles qu'ils ont posées
	
	public DrawTilePixelSum(ImageTree t, int sizeTile, AtomicInteger cpt, Color c) {
		this.t = t;
		this.sizeTile = sizeTile;
		this.cptGlobal = cpt;
		this.c = c;
	}

	/* Produit des tuiles et les pose tant que le thread n'est pas interrompu par la fermeture du pool */
	@Override
	public void run(){
		while(!Thread.currentThread().isInterrupted()) {
			t.putTile(new Tile(t, sizeTile), c);
			cpt++;
		}
		cptGlobal.addAndGet(cpt); // ajouter à la variable commune le nombre de tuile qu'on a posées
	}
	
}
