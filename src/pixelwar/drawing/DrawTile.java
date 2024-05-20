package pixelwar.drawing;

import java.awt.Color;

import pixelwar.tree.ImageTree;

public class DrawTile implements Runnable {
	private final Tile tile;
	private final ImageTree t;
	private final Color c;
	public DrawTile(ImageTree t, int sizeTile, Color c) {
		this.tile = new Tile(t, sizeTile); //construction de la tuile à poser
		this.t = t;
		this.c = c;
	}
	
	/* Pose la tuile */
	@Override
	public void run() {
		t.putTile(tile, c);
	}

}
