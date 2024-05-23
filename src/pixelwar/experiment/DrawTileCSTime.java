package pixelwar.experiment;

import java.awt.Color;
import java.util.concurrent.Callable;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;

public class DrawTileCSTime implements Callable<Long>{
	private final Tile tile;
	private final ImageTree t;
	private final Color c;
	public DrawTileCSTime(ImageTree t, int sizeTile, Color c) {
		this.tile = new Tile(t, sizeTile); //construction de la tuile à poser
		this.t = t;
		this.c = c;
	}
	
	
	/* Pose la tuile et retourne le temps d'attente du thread avant de pouvoir poser la tuile */
	public Long call() throws Exception {
		return t.putTileCS(tile, c);
	}
}
