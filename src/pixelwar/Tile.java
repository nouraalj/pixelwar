package pixelwar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tile {
	
	private final List<Pixel> pixels = new ArrayList<>();
	private final Pixel[][] matrix;	
	
	
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
			
		matrix = new Pixel[(xs[1] - xs[0])+1][(ys[1]- ys[0])+1];
		System.out.println(xs[0] + " " + xs[1]);
		System.out.println(ys[0] + " " + ys[1]);

		// boucle pour obtenir les pixels contenus entre les 4 coins : 
		for(int i = xs[0]; i < xs[1]+1; i++) {
			for (int j = ys[0]; j < ys[1]+1; j++) {
				Pixel p = m.getPixel(i, j);
				pixels.add(p);
				//matrix[i][j] = p;
			}
		}
	
	}
	
	public Tile(int n, ImageTree tree) {
		
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
		System.out.println(xs[0] + " " + xs[1]);
		System.out.println(ys[0] + " " + ys[1]);

		matrix = new Pixel[(xs[1] - xs[0])+1][(ys[1]- ys[0])+1];
		

			for(int i = xs[0]; i < xs[1]+1; i++) {
				for (int j = ys[0]; j < ys[1]+1; j++) {
				Pixel p = tree.findPixel(i, j);
				//System.out.println(p.toString());
				pixels.add(p);
				//matrix[j][i] = p;
			}
		}
	}
	
	
	public List<Pixel> getPixels() {
		return pixels;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Pixel p : pixels) {
			
			sb.append(p.toString() + " ");
		}
		return sb.toString();
	}

}
