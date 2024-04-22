package pixelwar.drawing;

import pixelwar.tree.ImageTree;

public class DrawTile implements Runnable {
	private final Tile tile;
	private final ImageTree t;

	public DrawTile(ImageTree t, int sizeTile) {
		//construction de la tuile 
		this.tile = new Tile(t, sizeTile);
		this.t = t;
	}
	
	@Override
	public void run() {
		t.putTile(tile);
	}

}
