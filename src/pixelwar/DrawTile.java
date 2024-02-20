package pixelwar;

public class DrawTile implements Runnable {
	private final Tile tile;
	private final ImageTree t;
	public DrawTile(Tile tile, ImageTree t) {
		/*id (tuile) pixel à poser*/
		this.tile = tile;
		this.t = t;
	}

	@Override
	public void run() {
		t.putTile(tile);
	}

}
