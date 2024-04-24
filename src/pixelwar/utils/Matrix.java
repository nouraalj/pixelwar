package pixelwar.utils;

import pixelwar.tree.Pixel;

/* Matrice de représentation de l'arbre / de la toile (utile pour les affichages) */

public class Matrix {
	private int sizeM; // dimension du côté de la matrice
	private Pixel[][] img; // matrice des pixels de l'image
	
	public Matrix(int size) {
		this.sizeM = size;
		img = new Pixel[sizeM][sizeM];
		
		// initialiser toute la matrice à null
		for (int i = 0; i< sizeM; i++) {
			for (int j = 0; j < sizeM; j++) {
				img[i][j] = null;	
			}
		}
	}

	/* Retourne la matrice */
	public Pixel[][] getImg() {
		return img;
	}
	
	/* Retourne le pixel de coordonées (x,y) */
	public Pixel getPixel(int x, int y) {
		return img[x][y];
		
	}
	
	/* Ajoute le pixel dans la matrice */
	public void setPixel(Pixel p) {
		img[p.getY()][p.getX()] = p;
	}
}
