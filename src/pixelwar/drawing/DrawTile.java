package pixelwar.drawing;

import pixelwar.tree.ImageTree;

public class DrawTile implements Runnable {
	private final Tile tile;
	private final ImageTree t;

	public DrawTile(ImageTree t, int sizeTile) {
		this.tile = new Tile(t, sizeTile); //construction de la tuile à poser
		this.t = t;
	}
	
	/* Pose la tuile */
	@Override
	public void run() {
		t.putTile(tile);
	}

}
