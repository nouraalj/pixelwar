package pixelwar;


public class Tile {

	private final int[] ids;
	
	/* il faut un constructeur qui génère aléatoirement la tuile*/
	
	
	public Tile( int[] ids) {
		this.ids = ids;
	}
	
	public int[] getIds() {
		return ids;
	}

}
