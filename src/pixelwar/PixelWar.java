package pixelwar;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pixelwar.strategy.ImageTreeInterMutex;
import pixelwar.strategy.ImageTreeMutex;
import pixelwar.strategy.ImageTreePixelMutex;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class PixelWar {
	

	// test de stratégie 2
	public static void main(String[] args) throws InterruptedException {
		ImageTreeInterMutex img = new ImageTreeInterMutex(4);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		for(int i = 0; i<4; i++) {
			pool.submit(new DrawTile(new Tile(img, 2), img)); // optimiser le passage des arguments, là on passe 2 fois l'arbre
		}
		pool.shutdown();
		pool.awaitTermination(15, TimeUnit.SECONDS); // attend que tous les threads aient terminé
		
		String path = "test_strat2.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);
	}
	
	
	// test des tuiles aléatoires avec stratégie 3
	public static void main0(String[] args) throws InterruptedException {

		ImageTreePixelMutex img = new ImageTreePixelMutex(4);
		
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		for(int i = 0; i<10; i++) {
			
			pool.submit(new DrawTile(new Tile(img, 2), img));
		}
		
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES); // attend que tous les threads aient terminé
		
		String path = "test_strat3.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);
		
		
	}
	
	
	// test des tuiles aléatoires avec stratégie 1
	public static void main1(String[] args) throws InterruptedException {

		ImageTreeMutex img = new ImageTreeMutex(16);
		
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		for(int i = 0; i<11; i++) {
			pool.submit(new DrawTile(new Tile(img, 2), img));
		}
		
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES); // attend que tous les threads aient terminé
		
		String path = "test_strat1.txt";
		System.out.println("Ouvrir le fichier " + path + " pour voir l'image résultat");
		img.exportImage(path);
	}
	
	
	// test des fonctions de Utils
	public static void main2(String[] args) throws Exception {
		
		int[] ints = {-1, 0, 1, 2, 3, 4, 8, 15};
		System.out.println("\nTest de log2int, log2double, isPowerOf2 :\n ");
		for(int i : ints) {
			System.out.println("log2int(" + i + ") = " + Utils.log2int(i));
		}
		System.out.println("");
		
		for(int i : ints) {
			System.out.println("log2double(" + i + ") = " + String.format("%.2f", Utils.log2double(i)));
		}
		System.out.println("");
		
		for(int i : ints) {
			System.out.println("isPowerOf2(" + i + ") = " + Utils.isPowerOf2(i));
		}
	}
	
	// test des fonctions sur les arbres (affichage, export, trouver un pixel, trouver un noeud cible, etc)
	public static void main3(String[] args) throws Exception {		
		
		int c = 16;
		System.out.println("\nCréation d'un arbre pour représenter une image carrée de côté " + c + "...");
		ImageTreeMutex img = new ImageTreeMutex(c);
		
		
		
		System.out.println("\n\n\nTest de findPixel(x, y) :");
		System.out.println("(Si pas d'exception jetée alors test OK, possibilité de décommenter l'affichage pour avoir les détails)");
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
		String filename = "essai.txt";
		img.exportImage(filename);
		System.out.println("Ouvrir le fichier '" + filename + "' pour voir le résultat de l'export");
		
	}
	
	
	// test des fonctions sur les tuiles
	public static void main4(String[] args) throws Exception {
		ImageTreeMutex img = new ImageTreeMutex(16);
		
		System.out.println("Test de sortById : \n");
		
		for(int i = 0; i<5; i++) {
			Tile t = new Tile(img, 4);
			System.out.println("Tuile avant tri par ordre croissant :\n" + t.toString());
			t = t.sortById();
			System.out.println("Tuile après tri par ordre croissant :\n" + t.toString());
			System.out.println();
		}
		
	}
		
	
	// test des fonctions sur les arbres spécifiques à la stratégie 2
	public static void main5(String[] args) {
		ImageTreeInterMutex img = new ImageTreeInterMutex(4);
		
		System.out.println("Test de pathToNode : \n");
		
		for(int i = 0; i<5; i++) {
			Tile t = new Tile(img, 2);
			System.out.println(t.toString());
			
			// calculer le chemin vers le noeud cible et récupérer la longueur de ce chemin
			Map<Integer, byte[]> path_ = img.pathToNode(t);
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
