package pixelwar.tests;

import java.util.Arrays;
import java.util.Map;

import pixelwar.drawing.Tile;
import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.tree.ImageTree;
import pixelwar.tree.Pixel;

public class TreeTest {

	public static void main(String[] args) throws Exception {
		int c = 16;
		System.out.println("\nCréation d'un arbre pour représenter une image carrée de côté " + c + "...");
		ImageTree img = new ImageTreeMutex(c);
		
		
		
		System.out.println("\n\n\nTest de findPixel(x, y) :");
		System.out.println("\\nTest avec des pixels existants : \\n(Si pas d'exception jetée alors test OK, possibilité de décommenter l'affichage pour avoir les détails)");
		for(int i = 0; i < img.getN(); i++) {
			for(int j = 0; j< img.getN(); j++) {
				//System.out.println("Looking for pixel : (" + i + "," + j + ")");
				
				Pixel p = img.findPixel(i, j);
				//System.out.println("Found pixel : " + p.toString() + "\n");
				
				if(p.sameCoordinates(i, j) == false) {
					throw new Exception("Echec du test de findPixel");
				}
			}
		}
		
		System.out.println("\nTest de findPixel(x, y) avec un pixel qui n'existe pas :");
		int iy = 0; int ix = img.getN(); ix += ix;
		System.out.println("Looking for pixel : (" + ix + "," + iy + ")");
		Pixel p0 = img.findPixel(ix, iy);
		System.out.println("Found pixel : " + (p0 == null ? "null" : p0.toString()) + "\n");
		
		
		
		
		System.out.println("\n\n\nTest de findPixel(id) :");
		System.out.println("\nTest avec des pixels existants : \n(Si pas d'exception jetée alors test OK, possibilité de décommenter l'affichage pour avoir les détails)");
		int n = img.getN();
		for(int i = 0; i < n*n; i++) {
			//System.out.println("Looking for pixel : " + i);
			
			Pixel p = img.findPixel(i);
			//System.out.println("Found pixel : " + p.toString() + "\n");
			
			if(p.getId() != i) {
				throw new Exception("Echec du test de findPixel");
			}
		}
		
		System.out.println("\nTest de findPixel(id) avec un pixel qui n'existe pas :");
		int ii = img.getN(); ii = (ii*ii);
		System.out.println("Looking for pixel : " + ii);
		Pixel p1 = img.findPixel(ii);
		System.out.println("Found pixel : " + (p1 == null ? "null" : p1.toString()) + "\n");
		
		
		
		System.out.println("\n\n\nTest de createMatrix :");
		System.out.println("Création de la matrice...");
		img.createMatrix();
		System.out.println("\nAffichage de la matrice : \n");
		img.showMatrix();
		
		System.out.println("\n\nTest de exportImage :");
		String filename = "data/test/essai.txt";
		img.exportImage(filename);
		System.out.println("Ouvrir le fichier '" + filename + "' pour voir le résultat de l'export");
		
		
		
		ImageTreeInterMutex img2 = new ImageTreeInterMutex(32);
		
		System.out.println("\n\n\n\n\nTest de pathToNode : \n");
		
		for(int i = 0; i<5; i++) {
			Tile t = new Tile(img2, 2);
			System.out.println("tuile à poser : " + t.toString());
			
			// calculer le chemin vers le noeud cible et récupérer la longueur de ce chemin
			Map<Integer, byte[]> path_ = img2.pathToNode(t);
			int length = 0;
			for (Integer j : path_.keySet()) { // supercherie pour récupérer l'unique clé de la map
				length = j;
			}
			byte[] path = path_.get(length);
			
			System.out.println("longueur du chemin : " + length);
			System.out.println("chemin vers le noeud cible couvrant la tuile : " + Arrays.toString(path));
			System.out.println();
		}
		

	}

}
