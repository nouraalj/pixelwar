package pixelwar.drawing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pixelwar.tree.ImageTree;
import pixelwar.tree.Pixel;

public class Tile {
	private List<Pixel> pixels = new ArrayList<>();	// liste des pixels contenus dans la tuile	
	
	public Tile(ImageTree tree, int sizeTile) {
		int n = tree.getN();
		
		// choisir 2 abscisses et 2 ordonnées au hasard (on suppose que les tuiles sont des carrés)
		Random random = new Random();
		int x1 = random.nextInt(n);
		int y1 = random.nextInt(n);

		// tant que la tuile ne rentre pas dans les dimensions de la toile, on re tire des coordonnées au hasard
		while( (x1 + sizeTile > n) || (y1+ sizeTile > n)) {
			x1 = random.nextInt(n);
			y1 = random.nextInt(n);
		}
		
		/* Trouver les pixels dans l'arbre et les mettre dans une liste */
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
			
			// chercher le prochain pixel d'id min
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
	
	
	/* Retourne le nombre de pixels de la tuile */
	public int nbPixels() {
		return pixels.size();
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Pixel p : pixels) {
			sb.append(p.toString() + " ");
		}
		return sb.toString();
	}
	
	

}
