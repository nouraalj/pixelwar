package pixelwar.utils;

import pixelwar.tree.Pixel;

public class Matrix {
	private int sizeM; // dimension du côté de la matrice
	private Pixel[][] img;
	
	public Matrix(int size) {
		this.sizeM = size;
		img = new Pixel[sizeM][sizeM];
		
		for (int i = 0; i< sizeM; i++) {
			for (int j = 0; j < sizeM; j++) {
				img[i][j] = null;	
			}
		}
	}

	public Pixel[][] getImg() {
		return img;
	}
	
	public Pixel getPixel(int x, int y) {
		return img[x][y];
		
	}
	
	public void setPixel(Pixel p) {
		img[p.getY()][p.getX()] = p;
	}
}
