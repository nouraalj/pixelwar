package pixelwar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tile {
	private List<Pixel> pixels = new ArrayList<>();	// liste des pixels contenus dans la tuile
	
	public Tile(int n, Matrix m) {
		Random random = new Random();
		
		//4 points (on suppose que les tuiles sont des carrés)
		int x1 = random.nextInt(n);
		int y1 = random.nextInt(n);
		int x2 = random.nextInt(n);
		int y2 = random.nextInt(n);
		
		int[] xs = {x1, x2};
		int[] ys = {y1, y2};
		
		Arrays.sort(xs);
		Arrays.sort(ys);
			
		//matrix = new Pixel[(xs[1] - xs[0])+1][(ys[1]- ys[0])+1];
		//System.out.println(xs[0] + " " + xs[1]);
		//System.out.println(ys[0] + " " + ys[1]);

		// boucle pour obtenir les pixels contenus entre les 4 coins : 
		for(int i = xs[0]; i < xs[1]+1; i++) {
			for (int j = ys[0]; j < ys[1]+1; j++) {
				Pixel p = m.getPixel(i, j);
				pixels.add(p);
			}
		}
	}
	
	
	/*public Tile(ImageTree tree) {
		
		int n = tree.getN();
		Random random = new Random();

		//4 points (on suppose que les tuiles sont des carrés)
		int x1 = random.nextInt(n);
		int y1 = random.nextInt(n);
		int x2 = random.nextInt(n);
		int y2 = random.nextInt(n);
		
		int[] xs = {x1, x2};
		int[] ys = {y1, y2};
		
		Arrays.sort(xs);
		Arrays.sort(ys);

		for(int i = xs[0]; i < xs[1]+1; i++) {
			for (int j = ys[0]; j < ys[1]+1; j++) {
			Pixel p = tree.findPixel(i, j);
			pixels.add(p);
			}
		}
	}
	*/
	
	public Tile(ImageTree tree, int sizeTile) {
		
		int n = tree.getN();
		Random random = new Random();

		//4 points (on suppose que les tuiles sont des carrés)
		
		int x1 = random.nextInt(n);
		int y1 = random.nextInt(n);

		while( (x1 + sizeTile > n) || (y1+ sizeTile > n)) {
			x1 = random.nextInt(n);
			y1 = random.nextInt(n);
		}
		
		for(int i = x1; i < x1+sizeTile; i++) {
			for (int j = y1; j < y1+sizeTile; j++) {
			Pixel p = tree.findPixel(i, j);
			pixels.add(p);
			}
		}
	}
	
	
	// retourne la liste des pixels de la tuile
	public List<Pixel> getPixels() {
		return pixels;
	}
	
	
	// retourne la tuile avec ses pixels classés par ordre croissant d'identifiant
	public Tile sortById() {
		List<Pixel> res = new ArrayList<>();
		List<Pixel> tmp = new ArrayList<>();
		tmp.addAll(this.pixels);
		
		while(tmp.size() != 0) {
			Pixel pmin = tmp.get(0);
			int idmin = tmp.get(0).getId();
			
			for(Pixel p : tmp) {
				if(p.getId() < idmin) {
					idmin = p.getId();
					pmin = p;
				}
			}
			res.add(pmin);
			tmp.remove(pmin);
		}
		
		this.pixels = res;
		return this;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Pixel p : pixels) {
			sb.append(p.toString() + " ");
		}
		return sb.toString();
	}

}
