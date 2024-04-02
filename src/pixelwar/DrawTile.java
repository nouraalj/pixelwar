package pixelwar;

public class DrawTile implements Runnable {
	private final Tile tile;
	private final ImageTree t;
	public DrawTile(Tile tile, ImageTree t) {
		/*id (tuile) pixel à poser*/
		this.tile = tile;
		this.t = t;
	}

	public DrawTile(ImageTree t, int sizeTile) {
		//construction de la tuile :
		Tile tile = new Tile(t, sizeTile);
		
		this.tile = tile;
		this.t = t;
	}
	
	@Override
	public void run() {
		t.putTile(tile);
	}

}
