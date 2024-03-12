package pixelwar;

public class DrawPixel implements Runnable {
	private final int id;
	private final ImageTree t;
	public DrawPixel(int id, ImageTree t) {
		/*id (tuile) pixel à poser*/
		this.id = id;
		this.t = t;
	}

	@Override
	public void run() {
		//t.putPixel();
	}

}